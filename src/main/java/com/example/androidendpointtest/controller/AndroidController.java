package com.example.androidendpointtest.controller;

import com.example.androidendpointtest.model.AndroidDevice;
import com.example.androidendpointtest.model.CarrierConfigType;
import com.example.androidendpointtest.model.ProvisionConfig;
import com.example.androidendpointtest.model.PushMessage;
import com.example.androidendpointtest.model.PushNotification;
import com.example.androidendpointtest.model.PushPriority;
import com.example.androidendpointtest.model.TermsAndConditions;
import com.example.androidendpointtest.model.TermsAndConditionsResponse;
import com.example.androidendpointtest.repo.AndroidDeviceRepo;
import com.example.androidendpointtest.service.PushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/android")
@Slf4j
public class AndroidController {

	private final PushService pushService;
	private final AndroidDeviceRepo androidDevicerepo;

	public AndroidController(PushService pushService, AndroidDeviceRepo androidDevicerepo) {
		this.pushService = pushService;
		this.androidDevicerepo = androidDevicerepo;
	}

	@GetMapping("/device")
	public List<AndroidDevice> getAllDevices(){
		return androidDevicerepo.findAll();
	}

	@PostMapping("/device")
	public AndroidDevice addDevice(@RequestBody AndroidDevice device){
		return androidDevicerepo.save(device);
	}

	@PostMapping("/push/{id}")
	public void push(@PathVariable long id, @RequestParam String action){

		log.info("Sending push notification");

		PushMessage pushMessage = new PushMessage(action, "Execute action");

		String deviceToken = androidDevicerepo.getAndroidDeviceById(id).getPushToken();


		PushNotification pushNotification = new PushNotification(
			deviceToken,
			PushPriority.HIGH.getValue(),
			pushMessage);

		pushService.sendMessage(pushNotification);
	}

	@GetMapping("/push/{id}")
	public void storePushToken(@PathVariable long id, @RequestParam String token){

		log.info("{} token received", token);

		AndroidDevice device = androidDevicerepo.getAndroidDeviceById(id);

		device.setPushToken(token);

		androidDevicerepo.save(device);
	}

	@GetMapping("/config/{id}")
	public ProvisionConfig getConfig(@PathVariable long id){
		log.info("Sending config");

		AndroidDevice device = androidDevicerepo.getAndroidDeviceById(id);

		return device.getProvisionConfig();
	}

	@PostMapping("/config/{id}")
	public AndroidDevice setConfig(@PathVariable long id, @RequestBody ProvisionConfig config){
		AndroidDevice device = androidDevicerepo.getAndroidDeviceById(id);

		device.setProvisionConfig(config);

		return androidDevicerepo.save(device);

	}

	@RequestMapping("/terms/{id}")
	public TermsAndConditions getTermsAndConditions(@PathVariable long id){

		return new TermsAndConditions("SF Terms&Conditions", "0.1", "Do you accept T&C provided by SF");
	}

	@PostMapping("/terms/{id}")
	public AndroidDevice replyTermsAndConditions(@RequestBody TermsAndConditionsResponse response, @PathVariable long id){
		log.info("Device responded {} to T&C", response.isAccepted());

		AndroidDevice device = androidDevicerepo.getAndroidDeviceById(id);

		device.setTcAccepted(response.isAccepted());

		return androidDevicerepo.save(device);
	}
}

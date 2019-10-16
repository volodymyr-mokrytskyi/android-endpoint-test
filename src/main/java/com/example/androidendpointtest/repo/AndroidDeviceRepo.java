package com.example.androidendpointtest.repo;

import com.example.androidendpointtest.model.AndroidDevice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AndroidDeviceRepo extends CrudRepository<AndroidDevice, Long> {
	AndroidDevice getAndroidDeviceById(Long id);
	List<AndroidDevice> findAll();
}

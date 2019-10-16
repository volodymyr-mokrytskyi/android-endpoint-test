package com.example.androidendpointtest.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AndroidDevice {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String pushToken;
	private boolean tcAccepted;

	@OneToOne(cascade = CascadeType.ALL)
	private ProvisionConfig provisionConfig;
}

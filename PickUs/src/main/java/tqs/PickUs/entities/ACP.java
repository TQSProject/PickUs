package tqs.PickUs.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "ACPs")
public class ACP {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(unique = true, nullable = false)
	private String name;

	@Column(nullable = false)
	private String city;

	@Column(name="enrolled_at", nullable = false, updatable = false)
	private String registed_time;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ACPStatus status;

	public ACP() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		Date date = new Date();
		registed_time = formatter.format(date);
		this.status = ACPStatus.WAITING_ADMIN_APPROVAL;
	}

	public ACP(String name, String city) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		Date date = new Date();
		registed_time = formatter.format(date);
		this.status = ACPStatus.WAITING_ADMIN_APPROVAL;
		this.name = name;
		this.city = city;
	}

	
	@Override
	public String toString() {
		return "ACP [id=" + id + ", name=" + name + ", city=" + city + ", registed_time=" + registed_time + ", status="
				+ status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ACP other = (ACP) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	public ACPStatus getStatus() {
		return status;
	}

	public void setStatus(ACPStatus status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegisted_time() {
		return registed_time;
	}

}

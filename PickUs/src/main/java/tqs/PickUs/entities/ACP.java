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
		return "ACP [id=" + id + ", name=" + name + ", city=" + city + ", status=" + status + "]";
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

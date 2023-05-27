package tqs.PickUs.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ACPStatus status;

	public ACP() {
		this.status = ACPStatus.WAITING_APPROVAL;
	}

	public ACP(String name, String city) {
		this.status = ACPStatus.WAITING_APPROVAL;
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


}

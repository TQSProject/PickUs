package tqs.PickUs.entities;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class CustomerOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Product> products;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Customer buyer;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private ACP dropOffSite;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public CustomerOrder() {
	}
	
	public CustomerOrder(List<Product> products, Customer buyer, ACP dropOffSite, Status status) {
		this.products = products;
		this.buyer = buyer;
		this.dropOffSite = dropOffSite;
		this.status = status;
	}
	
	// Getters and Setters
	
	public Long getId() {
		return id;
	}
	
	public List<Product> getProducts() {
		return products;
	}
	
	public Customer getBuyer() {
		return buyer;
	}
	
	public ACP getDropOffSite() {
		return dropOffSite;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
}

package tqs.PickUs.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String product;
	
	@OneToOne(cascade = CascadeType.ALL)
	private ACP acp;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public Order() {
	}
	
}

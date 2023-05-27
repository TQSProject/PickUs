package tqs.PickUs.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "Orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String store;

	@Column(nullable = false)
	private String buyer;

	@Column(nullable = false)
	private String product;

	@OneToOne(cascade = CascadeType.ALL)
	@Column(nullable = false)
	private ACP acp;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status;

	private LocalDateTime createdDateTime, approvedDateTime, estimatedDeliveryDateTime, deliveredDateTime,
			pickedUpDateTime;

	public Order() {
		this.createdDateTime = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", store=" + store + ", buyer=" + buyer + ", product=" + product + ", acp=" + acp
				+ ", status=" + status + "]";
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;

		if (status == OrderStatus.DELIVERING) {
			this.approvedDateTime = LocalDateTime.now();

			int minDelaySeconds = 15;
			int maxDelaySeconds = 30;
			// nextInt is normally exclusive of the top value,
			// so add 1 to make it inclusive
			int delaySeconds = ThreadLocalRandom.current().nextInt(minDelaySeconds, maxDelaySeconds + 1);

			this.estimatedDeliveryDateTime = LocalDateTime.now().plusSeconds(delaySeconds);
		} else if (status == OrderStatus.DELIVERED_AND_WAITING_FOR_PICKUP)
			this.deliveredDateTime = LocalDateTime.now();
		else if (status == OrderStatus.PICKED_UP)
			this.pickedUpDateTime = LocalDateTime.now();

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public ACP getAcp() {
		return acp;
	}

	public void setAcp(ACP acp) {
		this.acp = acp;
	}

}

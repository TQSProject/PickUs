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

	@Column(nullable = false)
	private int count;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private ACP acp;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status;

	private LocalDateTime createdDateTime, approvedDateTime, estimatedDeliveryDateTime, deliveredDateTime,
			pickedUpDateTime;

	public Order() {
		this.count = 1;
		this.setStatus(OrderStatus.WAITING_ADMIN_APPROVAL);
		this.createdDateTime = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", store=" + store + ", buyer=" + buyer + ", product=" + product + ", count=" + count
				+ ", acp=" + acp + ", status=" + status + "]";
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

	public OrderStatus getStatus() {
		return status;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public LocalDateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(LocalDateTime createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public LocalDateTime getApprovedDateTime() {
		return approvedDateTime;
	}

	public void setApprovedDateTime(LocalDateTime approvedDateTime) {
		this.approvedDateTime = approvedDateTime;
	}

	public LocalDateTime getEstimatedDeliveryDateTime() {
		return estimatedDeliveryDateTime;
	}

	public void setEstimatedDeliveryDateTime(LocalDateTime estimatedDeliveryDateTime) {
		this.estimatedDeliveryDateTime = estimatedDeliveryDateTime;
	}

	public LocalDateTime getDeliveredDateTime() {
		return deliveredDateTime;
	}

	public void setDeliveredDateTime(LocalDateTime deliveredDateTime) {
		this.deliveredDateTime = deliveredDateTime;
	}

	public LocalDateTime getPickedUpDateTime() {
		return pickedUpDateTime;
	}

	public void setPickedUpDateTime(LocalDateTime pickedUpDateTime) {
		this.pickedUpDateTime = pickedUpDateTime;
	}

}

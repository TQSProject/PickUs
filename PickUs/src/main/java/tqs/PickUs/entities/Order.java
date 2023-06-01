package tqs.PickUs.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.security.SecureRandom;

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
	
	public Order(String store, String buyer, String product, ACP acp) {
		this();
		this.setStore(store);
		this.store = store;
		this.buyer = buyer;
		this.product = product;
		this.acp = acp;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", store=" + store + ", buyer=" + buyer + ", product=" + product + ", count=" + count
				+ ", acp=" + acp + ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((store == null) ? 0 : store.hashCode());
		result = prime * result + ((buyer == null) ? 0 : buyer.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + count;
		result = prime * result + ((acp == null) ? 0 : acp.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((createdDateTime == null) ? 0 : createdDateTime.hashCode());
		result = prime * result + ((approvedDateTime == null) ? 0 : approvedDateTime.hashCode());
		result = prime * result + ((estimatedDeliveryDateTime == null) ? 0 : estimatedDeliveryDateTime.hashCode());
		result = prime * result + ((deliveredDateTime == null) ? 0 : deliveredDateTime.hashCode());
		result = prime * result + ((pickedUpDateTime == null) ? 0 : pickedUpDateTime.hashCode());
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
		Order other = (Order) obj;
		if (id != other.id)
			return false;
		if (store == null) {
			if (other.store != null)
				return false;
		} else if (!store.equals(other.store))
			return false;
		if (buyer == null) {
			if (other.buyer != null)
				return false;
		} else if (!buyer.equals(other.buyer))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (count != other.count)
			return false;
		if (acp == null) {
			if (other.acp != null)
				return false;
		} else if (!acp.equals(other.acp))
			return false;
		if (status != other.status)
			return false;
		if (createdDateTime == null) {
			if (other.createdDateTime != null)
				return false;
		} else if (!createdDateTime.equals(other.createdDateTime))
			return false;
		if (approvedDateTime == null) {
			if (other.approvedDateTime != null)
				return false;
		} else if (!approvedDateTime.equals(other.approvedDateTime))
			return false;
		if (estimatedDeliveryDateTime == null) {
			if (other.estimatedDeliveryDateTime != null)
				return false;
		} else if (!estimatedDeliveryDateTime.equals(other.estimatedDeliveryDateTime))
			return false;
		if (deliveredDateTime == null) {
			if (other.deliveredDateTime != null)
				return false;
		} else if (!deliveredDateTime.equals(other.deliveredDateTime))
			return false;
		if (pickedUpDateTime == null) {
			if (other.pickedUpDateTime != null)
				return false;
		} else if (!pickedUpDateTime.equals(other.pickedUpDateTime))
			return false;
		return true;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;

		if (status == OrderStatus.DELIVERING) {
			this.approvedDateTime = LocalDateTime.now();

			int minDelaySeconds = 15; // inclusive
			int maxDelaySeconds = 31; // exclusive
			// SecureRandom.nextInt(10) generates from 0 inclusive to 10 exclusive
			SecureRandom random = new SecureRandom();
			int delaySeconds = random.nextInt(maxDelaySeconds - minDelaySeconds) + minDelaySeconds;
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

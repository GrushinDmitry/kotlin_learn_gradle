package homework.lesson6.agency.model

import org.hibernate.Hibernate
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "sold_properties")
data class Property(
    @Id val id: Int,
    val address: String,
    val area: Int,
    val price: Int,
) {
    override fun toString(): String {
        return this::class.simpleName + "(id = $id, address = $address, area = $area, price = $price)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Property
        return id == other.id
    }

    override fun hashCode(): Int = 1756406093
}





package homework.lesson6.agency.model


import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "soldproperties")
data class Property(
    @Column val address: String,
    @Column val area: Int,
    @Column val price: Int,
    @Id @Column val id: Int
) {
    override fun toString(): String {
        return this::class.simpleName + "(address = $address , area = $area,price = $price , id = $id  )"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Property
        return id == other.id
    }

    override fun hashCode(): Int = 1756406093
}





package homework.lesson7.agency.model


import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "soldproperties")
data class Property(

    @Column(name = "address") val address: String,
    @Column(name = "area") val area: Int,
    @Column(name = "price") val price: Int,
    @Id @Column(name = "id") val id: Int


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





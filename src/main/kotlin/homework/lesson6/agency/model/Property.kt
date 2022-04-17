package homework.lesson6.agency.model

import org.hibernate.Hibernate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor

@Entity
@Table(name = "sold_properties")
data class Property(
    @Id
    @GeneratedValue
    val id: Int,
    val address: String,
    val area: Int,
    val price: Int,
) {
    override fun toString(): String {
        val value = mutableMapOf<String, Any?>()
        this::class.declaredMemberProperties.forEach { value[it.name] = it.call(this) }
        return this::class.simpleName + this::class.primaryConstructor?.parameters?.joinToString(
            prefix = "(",
            postfix = ")"
        )
        { "${it.name} = ${value[it.name]}" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Property
        return id == other.id
    }

    override fun hashCode(): Int = 1756406093
}






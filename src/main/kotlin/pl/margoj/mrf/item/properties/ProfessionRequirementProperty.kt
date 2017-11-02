package pl.margoj.mrf.item.properties

import pl.margoj.mrf.item.ItemProperty
import java.io.DataInputStream
import java.io.DataOutputStream

open class ProfessionRequirementProperty(
        id: Int,
        name: String,
        propertyName: String
) : ItemProperty<ProfessionRequirementProperty.ProfessionRequirement>(id, name, propertyName, ProfessionRequirement())
{
    override fun serialize(out: DataOutputStream, value: ProfessionRequirement)
    {
        var mask = 0

        if (value.warrior)
        {
            mask = mask or (1 shl 1)
        }
        if (value.paladin)
        {
            mask = mask or (1 shl 2)
        }
        if (value.bladedancer)
        {
            mask = mask or (1 shl 3)
        }
        if (value.mage)
        {
            mask = mask or (1 shl 4)
        }
        if (value.hunter)
        {
            mask = mask or (1 shl 5)
        }
        if (value.tracker)
        {
            mask = mask or (1 shl 6)
        }

        out.writeByte(mask)
    }

    override fun deserialize(input: DataInputStream): ProfessionRequirement
    {
        val mask = input.readByte().toInt()
        val requirements = ProfessionRequirement()

        if (mask and (1 shl 1) != 0)
        {
            requirements.warrior = true
        }
        if (mask and (1 shl 2) != 0)
        {
            requirements.paladin = true
        }
        if (mask and (1 shl 3) != 0)
        {
            requirements.bladedancer = true
        }
        if (mask and (1 shl 4) != 0)
        {
            requirements.mage = true
        }
        if (mask and (1 shl 5) != 0)
        {
            requirements.hunter = true
        }
        if (mask and (1 shl 6) != 0)
        {
            requirements.tracker = true
        }

        return requirements
    }

    data class ProfessionRequirement(
            var warrior: Boolean = false,
            var paladin: Boolean = false,
            var bladedancer: Boolean = false,
            var mage: Boolean = false,
            var hunter: Boolean = false,
            var tracker: Boolean = false
    )
    {
        val any: Boolean
            get()
            {
                return this.warrior || this.paladin || this.bladedancer || this.mage || this.hunter || this.tracker
            }
    }
}
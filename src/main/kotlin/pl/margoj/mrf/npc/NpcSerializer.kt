package pl.margoj.mrf.npc

import pl.margoj.mrf.serialization.MRFSerializer
import java.io.DataOutputStream

class NpcSerializer : MRFSerializer<MargoNpc>()
{
    companion object
    {
        val VERSION = 0x01.toByte()
    }

    override fun doSerialize(obj: MargoNpc, out: DataOutputStream)
    {
        out.writeByte(NpcSerializer.VERSION.toInt())
        out.writeUTF(obj.id)
        out.writeUTF(obj.name)
        out.writeInt(obj.level)
        this.writeEnum(out, obj.type)
        this.writeEnum(out, obj.rank)
        this.writeEnum(out, obj.gender)
        this.writeEnum(out, obj.profession)
        out.writeUTF(obj.script)
        out.writeUTF(obj.graphics)
        out.writeInt(obj.strength)
        out.writeInt(obj.agility)
        out.writeInt(obj.intellect)
        out.writeInt(obj.maxHp)
        out.writeInt(obj.attackSpeed)
        out.writeInt(obj.attack.start)
        out.writeInt(obj.attack.endInclusive)
        out.writeInt(obj.armor)
        out.writeInt(obj.block)
        out.writeInt(obj.evade)
        out.writeLong(obj.spawnTime)
    }

    private fun writeEnum(out: DataOutputStream, enum: NpcEnum)
    {
        out.writeByte(enum.id.toInt())
    }
}
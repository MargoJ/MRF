package pl.margoj.mrf.npc

import pl.margoj.mrf.serialization.MRFDeserializer
import pl.margoj.mrf.serialization.SerializationException
import java.io.DataInputStream

class NpcDeserializer : MRFDeserializer<MargoNpc>()
{
    override fun doDeserialize(input: DataInputStream): MargoNpc
    {
        val version = input.readByte()

        if (version > NpcSerializer.VERSION.toByte())
        {
            throw SerializationException("Version is invalid, map=$version, current=${NpcSerializer.VERSION}")
        }

        val obj = MargoNpc(version, input.readUTF(), input.readUTF())

        obj.level = input.readInt()
        obj.type = this.readEnum(input, NpcType.values())
        obj.rank = this.readEnum(input, NpcRank.values())
        obj.gender = this.readEnum(input, NpcGender.values())
        obj.profession = this.readEnum(input, NpcProfession.values())
        obj.script = input.readUTF()
        obj.graphics = input.readUTF()
        obj.strength = input.readInt()
        obj.agility = input.readInt()
        obj.intellect = input.readInt()
        obj.maxHp = input.readInt()
        obj.attackSpeed = input.readInt()
        obj.attack = input.readInt()..input.readInt()
        obj.armor = input.readInt()
        obj.block = input.readInt()
        obj.evade = input.readInt()

        if(version >= 0x01)
        {
            obj.spawnTime = input.readLong()
        }

        return obj
    }

    private fun <T : NpcEnum> readEnum(input: DataInputStream, enum: Array<T>): T
    {
        return NpcEnum.byId(enum, input.readByte())!!
    }
}
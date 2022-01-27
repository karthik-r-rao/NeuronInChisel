package nn

import chisel3._

class NNWire(width: Int) extends Bundle{
    val data = Bits(width.W)
    val valid = Bool()
}

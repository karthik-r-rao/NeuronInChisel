// include project-wide stuff here

package nn

import chisel3._

class NNWireSigned(width: Int) extends Bundle{
    val data = SInt(width.W)
    val valid = Bool()
}

class NNWireUnsigned(width: Int) extends Bundle{
    val data = UInt(width.W)
    val valid = Bool()
}
// include project-wide stuff here

package nn

import chisel3._

// bundle to be used for data signals throughout the project
class NNWire(width: Int) extends Bundle{
    val data = Bits(width.W)
    val valid = Bool()
}

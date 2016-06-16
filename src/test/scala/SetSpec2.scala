import org.scalatest.FunSpec

class SetSpec2 extends FunSpec {

  describe("A Set") {
    describe("when empty") {
      it("should have size 0") {
        assert(Set.empty.size == 0)
      }

      it("should produce NoSuchElementException when head is invoke") {
        intercept[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }

}

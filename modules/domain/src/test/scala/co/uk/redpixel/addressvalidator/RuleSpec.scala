package co.uk.redpixel.addressvalidator

import cats.syntax.option.*
import co.uk.redpixel.addressvalidator.Rule.MaxLength
import monix.newtypes.BuildFailure
import org.scalacheck.Gen
import weaver.scalacheck.Checkers
import weaver.SimpleIOSuite

object RuleSpec extends SimpleIOSuite with Checkers:

  test("Fail to allocate a zero or negative max length"):
    forall(Gen.chooseNum(Int.MinValue, 0)): n =>
      expect(
        MaxLength(n).left.exists:
          _.message == "Max length must be greater than 0".some
      )

  test("Succeed to allocate a positive max length"):
    forall(Gen.chooseNum(1, Int.MaxValue)): n =>
      expect(MaxLength(n) contains n)

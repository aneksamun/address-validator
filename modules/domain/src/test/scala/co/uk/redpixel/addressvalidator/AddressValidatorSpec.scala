package co.uk.redpixel.addressvalidator

import cats.data.{NonEmptyChain, Validated}
import cats.effect.{IO, Resource}
import cats.syntax.option.*
import co.uk.redpixel.addressvalidator.Rule.MaxLength
import org.scalacheck.Gen
import weaver.scalacheck.Checkers
import weaver.IOSuite

object AddressValidatorSpec extends IOSuite with Checkers with Fixtures:

  override type Res = AddressValidator

  override def sharedResource: Resource[IO, Res] =
    AddressValidator().toResource

  test("Fail when country code is missing"): validator =>
    IO(
      expect.same(
        validator
          .validate(
            AddressContactInfo(
              addresseeFirstName = None,
              addresseeLastName = None,
              addressLine1 = None,
              addressLine2 = None,
              addressLine3 = None,
              town = None,
              county = None,
              postcode = None,
              countryCode = None
            )
          ),
        Validated.invalid(
          NonEmptyChain(
            ValidationError.invalidCountryCode(countryCode = None)
          )
        )
      )
    )

  test("Validate fields optionality in UK address"): validator =>
    IO(
      expect.same(
        validator
          .validate(
            addressContactInfo = AddressContactInfo(
              addresseeFirstName = None,
              addresseeLastName = None,
              addressLine1 = None,
              addressLine2 = None,
              addressLine3 = None,
              town = None,
              county = None,
              postcode = None,
              countryCode = "UK".some
            )
          ),
        Validated.invalid(
          NonEmptyChain(
            ValidationError.requiredFieldMissing("addresseeFirstName"),
            ValidationError.requiredFieldMissing("addresseeLastName"),
            ValidationError.requiredFieldMissing("addressLine1"),
            ValidationError.requiredFieldMissing("town")
          )
        )
      )
    )

  test("Validate fields optionality in US address"): validator =>
    IO(
      expect.same(
        validator
          .validate(
            addressContactInfo = AddressContactInfo(
              addresseeFirstName = None,
              addresseeLastName = None,
              addressLine1 = None,
              addressLine2 = None,
              addressLine3 = None,
              town = None,
              county = None,
              postcode = None,
              countryCode = "US".some
            )
          ),
        Validated.invalid(
          NonEmptyChain(
            ValidationError.requiredFieldMissing("addresseeFirstName"),
            ValidationError.requiredFieldMissing("addresseeLastName"),
            ValidationError.requiredFieldMissing("addressLine3"),
            ValidationError.requiredFieldMissing("town"),
            ValidationError.requiredFieldMissing("county")
          )
        )
      )
    )

  test("Validate length of the fields in UK address"): validator =>
    forall(Gen.stringOfN(41, Gen.alphaChar)): s =>
      expect.same(
        validator.validate(
          AddressContactInfo(
            addresseeFirstName = s.some,
            addresseeLastName = s.some,
            addressLine1 = s.some,
            addressLine2 = s.some,
            addressLine3 = s.some,
            town = s.some,
            county = s.some,
            postcode = s.some,
            countryCode = "UK".some
          )
        ),
        Validated.invalid(
          NonEmptyChain(
            ValidationError.invalidLength("addresseeFirstName", MaxLength.of(40)),
            ValidationError.invalidLength("addresseeLastName", MaxLength.of(40)),
            ValidationError.invalidLength("addressLine1", MaxLength.of(29)),
            ValidationError.invalidLength("addressLine2", MaxLength.of(35)),
            ValidationError.invalidLength("addressLine3", MaxLength.of(29)),
            ValidationError.invalidLength("town", MaxLength.of(35)),
            ValidationError.invalidLength("county", MaxLength.of(35)),
            ValidationError.invalidLength("postcode", MaxLength.of(8)),
            ValidationError.invalidField("postcode")
          )
        )
      )

  test("Validate length of the fields in US address"): validator =>
    forall(Gen.stringOfN(41, Gen.alphaChar)): s =>
      expect.same(
        validator.validate(
          AddressContactInfo(
            addresseeFirstName = s.some,
            addresseeLastName = s.some,
            addressLine1 = s.some,
            addressLine2 = None,
            addressLine3 = s.some,
            town = s.some,
            county = s.some,
            postcode = s.some,
            countryCode = "US".some
          )
        ),
        Validated.invalid(
          NonEmptyChain(
            ValidationError.invalidLength("addresseeFirstName", MaxLength.of(40)),
            ValidationError.invalidLength("addresseeLastName", MaxLength.of(40)),
            ValidationError.invalidLength("addressLine1", MaxLength.of(20)),
            ValidationError.invalidLength("addressLine3", MaxLength.of(29)),
            ValidationError.invalidLength("town", MaxLength.of(35)),
            ValidationError.invalidLength("county", MaxLength.of(35)),
            ValidationError.invalidLength("postcode", MaxLength.of(8)),
            ValidationError.invalidField("postcode")
          )
        )
      )

  test("Validate validity of the fields in UK address"): validator =>
    forall(Gen.stringOfN(8, Gen.alphaChar)): s =>
      expect.same(
        validator.validate(
          validUkAddress.copy(postcode = s.some)
        ),
        Validated.invalidNec(
          ValidationError.invalidField("postcode")
        )
      )

  test("Validate validity of the fields in US address"): validator =>
    forall(Gen.stringOfN(8, Gen.alphaChar)): s =>
      expect.same(
        validator.validate(
          validUsAddress.copy(postcode = s.some)
        ),
        Validated.invalidNec(
          ValidationError.invalidField("postcode")
        )
      )

  test("Succeeds when UK address is valid"): validator =>
    IO(expect(validator.validate(validUkAddress).isValid))

  test("Succeeds when US address is valid"): validator =>
    IO(expect(validator.validate(validUsAddress).isValid))

end AddressValidatorSpec

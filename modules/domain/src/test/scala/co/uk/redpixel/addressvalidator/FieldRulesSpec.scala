package co.uk.redpixel.addressvalidator

import cats.effect.IO
import cats.syntax.eq.*
import co.uk.redpixel.addressvalidator.Rule.MaxLength
import weaver.SimpleIOSuite

object FieldRulesSpec extends SimpleIOSuite:

  test("Load field rules"):
    FieldRules.load[IO].map: fieldRules =>
      expect(fieldRules === Map(
        "us" -> Map(
          "addresseeFirstName" -> Rule(
            maxLength = MaxLength.of(40),
            required = true
          ),
          "addresseeLastName" -> Rule(
            maxLength = MaxLength.of(40),
            required = true
          ),
          "addressLine1" -> Rule(
            maxLength = MaxLength.of(20),
            required = false
          ),
          "addressLine3" -> Rule(
            maxLength = MaxLength.of(29),
            required = true
          ),
          "town" -> Rule(
            maxLength = MaxLength.of(35),
            required = true
          ),
          "county" -> Rule(
            maxLength = MaxLength.of(35),
            required = true
          ),
          "postcode" -> Rule(
            maxLength = MaxLength.of(8),
            required = false,
            pattern = Some("^[0-9]{5}(?:-[0-9]{4})?$".r)
          ),
          "countryCode" -> Rule(
            maxLength = MaxLength.of(2),
            required = true
          )
        ),
        "uk" -> Map(
          "addresseeFirstName" -> Rule(
            maxLength = MaxLength.of(40),
            required = true
          ),
          "addresseeLastName" -> Rule(
            maxLength = MaxLength.of(40),
            required = true
          ),
          "addressLine1" -> Rule(
            maxLength = MaxLength.of(29),
            required = true
          ),
          "addressLine2" -> Rule(
            maxLength = MaxLength.of(35),
            required = false
          ),
          "addressLine3" -> Rule(
            maxLength = MaxLength.of(29),
            required = false
          ),
          "town" -> Rule(
            maxLength = MaxLength.of(35),
            required = true
          ),
          "county" -> Rule(
            maxLength = MaxLength.of(35),
            required = false
          ),
          "postcode" -> Rule(
            maxLength = MaxLength.of(8),
            required = false,
            pattern = Some("(([AC-FHKNPRTV-Y]\\d{2}|D6W)[0-9AC-FHKNPRTV-Y]{4}|[A-Z]{1,2}[0-9R][0-9A-Z]?[ ]?[0-9][ABD-HJLNP-UW-Z]{2})".r)
          ),
          "countryCode" -> Rule(
            maxLength = MaxLength.of(2),
            required = true
          )
        )
      )
    )


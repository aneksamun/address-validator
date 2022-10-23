package co.uk.redpixel.validator

import co.uk.redpixel.validator.rules.ValidationRules._
import io.circe.parser._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AddressValidatorSpec extends AnyFlatSpec with Matchers {

  val json =
    """
      |{
      |  "fields":[
      |    {
      |      "name":"addresseeFirstName",
      |      "rules":{
      |        "required":{
      |          "value":true
      |        },
      |        "maxLength":{
      |          "value":40
      |        }
      |      }
      |    },
      |    {
      |      "name":"addresseeLastName",
      |      "rules":{
      |        "required":{
      |          "value":true
      |        },
      |        "maxLength":{
      |          "value":40
      |        }
      |      }
      |    },
      |    {
      |      "name":"addressLine1",
      |      "rules":{
      |        "required":{
      |          "value":true
      |        },
      |        "maxLength":{
      |          "value":29
      |        }
      |      }
      |    },
      |    {
      |      "name":"addressLine2",
      |      "rules":{
      |        "maxLength":{
      |          "value":35
      |        }
      |      }
      |    },
      |    {
      |      "name":"addressLine3",
      |      "rules":{
      |        "maxLength":{
      |          "value":29
      |        }
      |      }
      |    },
      |    {
      |      "name":"town",
      |      "rules":{
      |        "required":{
      |          "value":true
      |        },
      |        "maxLength":{
      |          "value":35
      |        }
      |      }
      |    },
      |    {
      |      "name":"county",
      |      "rules":{
      |        "maxLength":{
      |          "value":35
      |        }
      |      }
      |    },
      |    {
      |      "name":"postcode",
      |      "rules":{
      |        "maxLength":{
      |          "value":8
      |        },
      |        "pattern":{
      |          "value":"(([AC-FHKNPRTV-Y]\\d{2}|D6W)[0-9AC-FHKNPRTV-Y]{4}|[A-Z]{1,2}[0-9R][0-9A-Z]?[ ]?[0-9][ABD-HJLNP-UW-Z]{2})"
      |        }
      |      }
      |    },
      |    {
      |      "name":"countryCode",
      |      "rules":{
      |        "required":{
      |          "value":true
      |        },
      |        "maxLength":{
      |          "value":2
      |        }
      |      }
      |    }
      |  ]
      |}
      |""".stripMargin

  "Address validator" should "validate an address" in {
    val a = decode[FieldRules](json)
    a.isRight should be(true)
  }
}

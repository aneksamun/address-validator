package co.uk.redpixel.addressvalidator

type FieldName  = String
type FieldRules = Map[FieldName, Rules]

object FieldRules:
  def load = ???

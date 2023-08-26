package co.uk.redpixel.addressvalidation.service.config

import com.comcast.ip4s.{Hostname, Port}
import pureconfig.*
import pureconfig.error.CannotConvert
import pureconfig.generic.derivation.default.*
import pureconfig.ConfigReader.Result

case class HttpServerConfig(host: Hostname, port: Port) derives ConfigReader

object HttpServerConfig:

  given ConfigReader[Hostname] =
    ConfigReader[String].emap: value =>
      Hostname.fromString(value).toRight(CannotConvert(value, "Hostname", "Invalid hostname"))

  given ConfigReader[Port] =
    ConfigReader[String].emap: value =>
      Port.fromString(value).toRight(CannotConvert(value, "Port", "Invalid port"))

package com.ibm.kernel.protocol.v5.content

import org.scalatest.{FunSpec, Matchers}
import play.api.data.validation.ValidationError
import play.api.libs.json.{JsPath, JsValue, Json}

/**
 * Created by Senkwich on 7/24/14.
 */
class ConnectReplySpec extends FunSpec with Matchers {


  val connectReplyJson: JsValue = Json.parse("""
  {
    "shell_port": 11111,
    "iopub_port": 22222,
    "stdin_port": 33333,
    "hb_port": 44444
  }
  """)

  val connectReply: ConnectReply = ConnectReply(11111,22222,33333,44444)

  describe("ConnectReply") {
    describe("implicit conversions") {
      it("should implicitly convert from valid json to a ConnectReply instance") {
        // This is the least safe way to convert as an error is thrown if it fails
        connectReplyJson.as[ConnectReply] should be (connectReply)
      }

      it("should also work with asOpt") {
        // This is safer, but we lose the error information as it returns
        // None if the conversion fails
        val newConnectReply = connectReplyJson.asOpt[ConnectReply]

        newConnectReply.get should be (connectReply)
      }

      it("should also work with validate") {
        // This is the safest as it collects all error information (not just first error) and reports it
        val ConnectReplyResults = connectReplyJson.validate[ConnectReply]

        ConnectReplyResults.fold(
          (invalid: Seq[(JsPath, Seq[ValidationError])]) => println("Failed!"),
          (valid: ConnectReply) => valid
        ) should be (connectReply)
      }

      it("should implicitly convert from a ConnectReply instance to valid json") {
        Json.toJson(connectReply) should be (connectReplyJson)
      }
    }
  }

}

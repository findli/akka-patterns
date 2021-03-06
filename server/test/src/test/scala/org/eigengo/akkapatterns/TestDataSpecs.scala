package org.eigengo.akkapatterns

import org.eigengo.akkapatterns.domain._
import com.mongodb.DB
import org.eigengo.scalad.mongo.sprayjson._
import java.util.UUID
import org.eigengo.akkapatterns.MongoCollectionFixture._

class TestDataSpecs extends NoActorSpecs with CleanMongo with CustomerMongo with TestCustomerData {

  "Mongo Test Data" should {
    "be clean at the beginning" in {
      configured[DB].getCollectionNames.size === 0
    }

    "customers fixture should attach" in new Fix("customers") {
      mongo.count[Customer]() must beGreaterThan(0L)
      mongo.findOne[Customer]("id" :> TestCustomerJanId) must beLike {
        case Some(customer) if customer.firstName == "Jan" => ok
      }
      mongo.findOne[Customer]("id" :> UUID.randomUUID()) === None
    }
  }
}

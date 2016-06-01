package models

import play.api.libs.json.Json
import reactivemongo.bson.BSONObjectID

case class Identification(
  _id: BSONObjectID = BSONObjectID.generate,
  imageUrl: String,
  featureVector: Option[FeatureVector] = None,
  classification: Option[Classification] = None)

object Identification {
  implicit val identificationFormat = Json.format[Identification]
}

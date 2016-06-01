package dao

import models.Identification
import play.api.libs.concurrent.Execution.Implicits._
import play.modules.reactivemongo.json.collection.JSONCollection

trait IdentificationDAOComponent {
  val identificationDAO: IdentificationDAO
}

trait MongoIdentificationDAOComponent extends IdentificationDAOComponent {
  val identificationDAO = new IdentificationMongoDAO
}

trait IdentificationDAO extends BaseDAO[Identification] {
}

class IdentificationMongoDAO extends BaseReactiveMongoDAO[Identification] with IdentificationDAO {
  def coll = db.collection[JSONCollection]("Identification")
}

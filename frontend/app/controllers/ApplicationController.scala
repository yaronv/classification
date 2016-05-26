package controllers

import play.api.mvc._

trait ApplicationController extends Controller {

  def index = Action {
    Ok(views.html.index("Online Classification Tool"))
  }

}

object ApplicationController extends ApplicationController

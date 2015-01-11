package controllers

import models.User
import securesocial.core.RuntimeEnvironment

class Application(override implicit val env: RuntimeEnvironment[User]) extends securesocial.core.SecureSocial[User] {

  def index = SecuredAction { implicit request =>
    Ok(views.html.main())
  }

  /**
   * Workaround because of hard-coded bootstrap link in SecureSocial
   * @return
   */
  def bootstrap() = WebJarAssets.at(WebJarAssets.locate("css/bootstrap.min.css"))
}

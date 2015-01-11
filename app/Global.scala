import java.lang.reflect.Constructor

import models.User
import securesocial.core.RuntimeEnvironment
import securesocial.core.providers._
import services.UserService

import scala.collection.immutable.ListMap

object Global extends play.api.GlobalSettings {

  /**
   * Application's custom Runtime Environment
   */
  object RuntimeEnvironment extends securesocial.core.RuntimeEnvironment.Default[User] {
    override lazy val userService: UserService = new UserService
    override lazy val providers = ListMap(
      include(new FacebookProvider(routes, cacheService, oauth2ClientFor(FacebookProvider.Facebook))),
      include(new GitHubProvider(routes, cacheService, oauth2ClientFor(GitHubProvider.GitHub))),
//      include(new GoogleProvider(routes, cacheService, oauth2ClientFor(GoogleProvider.Google))),
//      include(new LinkedInProvider(routes, cacheService, oauth1ClientFor(LinkedInProvider.LinkedIn))),
      include(new TwitterProvider(routes, cacheService, oauth1ClientFor(TwitterProvider.Twitter))),
      include(new UsernamePasswordProvider[User](userService, avatarService, viewTemplates, passwordHashers))
    )
  }

  /**
   * Dependency injection on Controllers using Cake Pattern
   *
   * @param controllerClass
   * @tparam A
   * @return
   */
  override def getControllerInstance[A](controllerClass: Class[A]): A = {
    val instance = controllerClass.getConstructors.find { c =>
      val params = c.getParameterTypes
      params.length == 1 && params(0) == classOf[RuntimeEnvironment[User]]
    }.map {
      _.asInstanceOf[Constructor[A]].newInstance(RuntimeEnvironment)
    }
    instance.getOrElse(super.getControllerInstance(controllerClass))
  }

}
/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rhttpc.akkahttp.json4s

import java.text.ParseException

import akka.http.scaladsl.model.ContentType
import org.json4s.CustomSerializer
import org.json4s.JsonAST.{JObject, JString}

object ContentTypeSerializer extends CustomSerializer[ContentType](implicit formats => (
  {
    case JObject(_ :: ("value", JString(value)) :: Nil) =>
      ContentType.parse(value).right.getOrElse(throw new ParseException("Illegal content-type: " + value, -1))
  },
  {
    case ct: ContentType => JObject(
      formats.typeHintFieldName -> JString(classOf[ContentType].getName),
      "value" -> JString(ct.toString())
    )
  }
))
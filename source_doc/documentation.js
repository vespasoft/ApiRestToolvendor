/**
 * @apiDefine ErrorAlBorrar
 *
 * @apiError    success:false   ocurrio un error la ejecucion de la solicitud de borrado
 * @apiError    message         mensaje descriptivo del error devuelto desde el servidor
 *
 * @apiErrorExample Error-Response:
 *     HTTP/1.1 404 Not Found
 *     {
 *       "success": false,
 *       "message": "Ocurrio un error al intentar borrar"
 *     }
 */

/**
 * @apiDefine SuccessAlBorrar
 *
 * @apiSuccess  {Boolean}   success     devuelve <code>true</code> en caso de exito
 * @apiSuccess  {String}    message     mensaje descriptivo de la informacion devuelta desde el servidor
 *
 * @apiSuccessExample Success-Response:
 *     HTTP/1.1 200 OK
 *     {
 *       "success": true,
 *       "message": "El registro se eliminó exitosamente."
 *     }
 */


/**
 * @apiDefine RespuestaSuccessListar
 *
 * @apiSuccess    {Boolean}     success     devuelve true si la solicitud tuvo exito.
 * @apiSuccess    {Object[]}    result      contiene un arreglo de objetos en formato json, contenedor de todos los registros.
 *
 * @apiSuccessExample Success-Response:
 *     HTTP/1.1 200 OK
 *     {
 *       "success" : true,
 *       "result"  : [
 *              {
 *                  "id" : 1,
 *                  "field_1" : "valor 1",
 *                  "field_2" : "valor 2",
 *                  "field_n" : "valor n"
 *              }
 *          ]
 *     }
 */

/**
 * @apiDefine ErrorAlEditar
 *
 * @apiError    success:false   ocurrio un error la ejecucion de la solicitud de edición
 * @apiError    message         mensaje descriptivo del error devuelto desde el servidor
 *
 * @apiErrorExample Error-Response:
 *     HTTP/1.1 404 Not Found
 *     {
 *       "success": false,
 *       "message": "No se encontro el id X"
 *     }
 */

/**
 * @apiDefine RespuestaSuccessInsertarEditar
 *
 * @apiSuccess    {Boolean}     success     devuelve <code>true</code> si la solicitud tuvo exito.
 * @apiSuccess    {String}      mesasge     Descripción del mensaje devuelto desde el servidor.
 * @apiSuccess    {Object[]}    result      contiene un arreglo de objetos en formato <code>json</code>, contenedor de todos los registros.
 *
 * @apiSuccessExample Success-Response:
 *     HTTP/1.1 200 OK
 *     {
 *       "success" : true,
 *       "message" : "El registro se guardó exitosamente.",
 *       "result"  : [
 *              {
 *                  "id" : 135e656e6cda8640820350816ab59f2d,
 *                  "field_1" : "valor 1",
 *                  "field_2" : "valor 2",
 *                  "field_n" : "valor n"
 *              }
 *          ]
 *     }
 */

/**
 * @apiDefine RespuestaSuccessLogin
 *
 * @apiSuccess    {Boolean}     success     devuelve <code>true</code> si los datos fueron validados y autorizados por el servidor.
 * @apiSuccess    {String}      mesasge     Descripción del mensaje devuelto desde el servidor.
 * @apiSuccess    {Object[]}    result      contiene un arreglo de objetos en formato <code>json</code>, de la entidad autorizada.
 *
 * @apiSuccessExample Success-Response:
 *     HTTP/1.1 200 OK
 *     {
 *           "success": true,
 *           "message": "Bienvenido al sistema toolvendor",
 *           "tokenId": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNDg1ODY2OTQ3LCJzdWIiOiIxIiwiaXNzIjoiTHVpcyBNYW51ZWwgVmVzcGEifQ.4MM6CZxv1NVCDo5Fugqs-h3ERcQWhMCuH8stTcETglM",
 *           "result": {
 *                  "id": 1,
 *                  "createdAt": 1483594440000,
 *                  "email": "vespaluis@gmail.com",
 *                  "enabled": true,
 *                  "lastUpdate": null,
 *                  "latitud": null,
 *                  "longitude": null,
 *                  "name": "Luis Manuel Vespa",
 *                  "password": "vt1cowt0",
 *                  "phone": "+50760846611",
 *                  "photo": "user.png",
 *                  "usertype": {
 *                    "id": 1,
 *                    "description": "SUPER ADMIN",
 *                    "type": "A",
 *                    "companyId": 1
 *                  },
 *                  "companyId": 1,
 *                  "city": {
 *                    "id": 1,
 *                    "country": {
 *                      "id": 1,
 *                      "name": "Panama",
 *                      "alpha2": "PA",
 *                      "alpha3": "PAN",
 *                      "number": null
 *                    },
 *                    "city": "Ciudad Panama"
 *                  },
 *                  "country": {
 *                    "id": 1,
 *                    "name": "Panama",
 *                    "alpha2": "PA",
 *                    "alpha3": "PAN",
 *                    "number": null
 *                  }
 *           }
 *       }
 */


/**
 * @apiDefine RespuestaFallidaLogin
 *
 * @apiError    {Boolean}     success     devuelve <code>false</code> si los datos NO fueron autorizados por el servidor para iniciar sesion.
 * @apiError    {String}      mesasge     Descripción del mensaje devuelto desde el servidor.
 * @apiError    {Object[]}    result      devuelve <code>null</code>, debido a que los parametros suministrados no fue validados por el servidor.
 *
 * @apiErrorExample
 *      HTTP/1.1 401 Unauthorized
 *     {
 *          "success": false,
 *          "message": "The username or password is invalid."
 *      }
 */


 // ------------------------------------------------------------------------------------------
 // A U T H E N T I C A T I O N .
 // ------------------------------------------------------------------------------------------
 /**
  * @api        {post}         /user/auth      Crear
  * @apiVersion 1.0.0
  * @apiName  UserAuthentication
  * @apiGroup Authentication
  *
  * @apiExample {post} Ejemplo de Solicitud:
  *                      http://toolvendor-beecode.rhcloud.com/rest/user/auth
  *
  * @apiDescription   Este método se utiliza para autenticar a un usuario. Las columnas requeridas son <code> email, password</code>.
  *                   La solicitud retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito,
  *                   de lo contrario devuelve <code>status 400</code>. Para mas informacion vease los ejemplos a continuación.
  *
  * @apiParam    {String}     email         Email del usuario.
  * @apiParam    {String}     password      Password del usuario.
  *
  *
  * @apiUse  RespuestaSuccessLogin
  *
  * @apiUse  RespuestaFallidaLogin
  *
  */


  // ------------------------------------------------------------------------------------------
  // C R E A T E  C O M P A N Y.
  // ------------------------------------------------------------------------------------------
  /**
   * @api      {post}         /company      Crear
   * @apiVersion 1.0.0
   * @apiName  CreateCompany
   * @apiGroup Empresas
   *
   * @apiExample {post} Ejemplo de Solicitud:
   *                      http://toolvendor-beecode.rhcloud.com/rest/company
   *
   * @apiDescription  Crea un nuevo objeto Company. Las columnas guardar son <code> company, contactName, email, phone, address, building, city, postalCode</code>.
   *                   La solicitud retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito,
   *                   de lo contrario devuelve <code>status 400</code>. Para mas informacion vease los ejemplos a continuación.
   *
   * @apiParam    {String}     company         Id de la compañia del usuario.
   * @apiParam    {String}     contactName     La descripción de la Categoria que se va a mostrar al usuario.
   * @apiParam    {String}     email           Email de la empresa y/o usuario
   * @apiParam    {String}     phone           Número de telefono
   * @apiParam    {String}     address         Dirección básica de la empresa
   * @apiParam    {String}     building        Dirección del edificio u oficina
   * @apiParam    {String}     city            Object[City] con los datos de la ciudad y el pais.
   * @apiParam    {String}     postalCode      Codigo Postal de la empresa.
   *
   * @apiUse  RespuestaSuccessInsertarEditar
   */

   /**
    * @api      {get}         /company/:id   Leer
    * @apiVersion 1.0.0
    * @apiName  GetCompany
    * @apiGroup Empresas
    *
    * @apiDescription  Recupera los datos de la Empresa cliente. Suministrar el <code>id</code> de la Empresa.
    *                  que se obtiene cuando el usuario se autentica. La respuesta devuelve un conjunto de variables, la variable
    *                  <code>result</code> contiene JSON con las siguientes columnas: <code>id, company, contactName, email, phone,
    *                  address, active, building, postalCode, country, city</code>.
    * @apiVersion 1.0.0
    * @apiParam {Number}       id              un numero entero (unique ID - PrimaryKey) para leer en la tabla
    * @apiName  GetCategoria
    * @apiGroup Categoria
    *
    * @apiExample {get} Ejemplo de Solicitud:
    *                      http://toolvendor-beecode.rhcloud.com/rest/company/1
    *
    * @apiParam    {String}        Access-Token  El método requiere Token en el Header de la petición
    *
    * @apiUse  RespuestaSuccessListar
    */



  // ------------------------------------------------------------------------------------------
  // U S U A R I O S.
  // ------------------------------------------------------------------------------------------

      /**
       * @api      {get}          /user      Listar
       * @apiVersion 1.0.0
       * @apiName  GetAll
       * @apiGroup Usuarios
       *
       * @apiExample {get} Ejemplo de Solicitud:
       *                      http://toolvendor-beecode.rhcloud.com/rest/user
       *
       * @apiDescription  Devuelve una lista de todas los Usuarios. El método retirna un listado de Usuarios pertenecientes a la Compañia,
       *                  las Categorias más recientemente creadas aparecen en primer lugar. Las columnas disponibles a mostrar son
       *                  <code>id, createdAt, email, enabled, latitud, longitude, name, phone, photo, usertype, companyId, city, country</code>
       * @apiName  GetAll
       * @apiGroup Usuarios
       *
       * @apiParam {String}       Access-Token  El método requiere Token en el Header de la petición
       *
       * @apiUse  RespuestaSuccessListar
       */

      /**
       * @api      {post}         /user      Crear
       * @apiVersion 1.0.0
       * @apiName  CreateUsuario
       * @apiGroup Usuarios
       *
       * @apiExample {post} Ejemplo de Solicitud:
       *                      http://toolvendor-beecode.rhcloud.com/rest/user
       *
       * @apiDescription  Crea un nuevo objeto User. Las columnas guardar son <code> email, enabled, latitud, longitude, name, phone, photo, usertype, companyId, city, country</code>.
       *                   La solicitud retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito,
       *                   de lo contrario devuelve <code>status 400</code>. Para mas informacion vease los ejemplos a continuación.
       *
       * @apiParam    {String}     Access-Token  El método requiere Token en el Header de la petición
       * @apiParam    {String}     companyId    Id de la compañia del usuario
       * @apiParam    {String}     email        El email del usuario.
       * @apiParam    {String}     [enabled]      El estatus del usuario si esta activo o inactivo para usar la plataforma.
       * @apiParam    {String}     [latitud]      Numero de Latitud de geolocalización GPS del usuario
       * @apiParam    {String}     [longitude]    Numero de Longitude de geolocalización GPS del usuario
       * @apiParam    {String}     name         Nombre del usuario
       * @apiParam    {String}     phone        Numero de telefono del usuario
       * @apiParam    {String}     [photo]        URL de la fotogradía del usuario
       * @apiParam    {String}     usertype     Objeto[UserType] que indica el tipo de usuario.
       * @apiParam    {String}     city         Objeto[City] que indica la ciudad y pais del usuario.
       *
       * @apiUse  RespuestaSuccessInsertarEditar
       */

      /**
       * @api      {get}          /user/:id   Leer
       * @apiDescription  Recupera los detalles de un Usuario existente. Suministrar el <code>id</code> del Usuario para devolver la
       *                  información correspondiente. La respuesta devuelve un conjunto de variables, la variable
       *                  <code>result</code> contiene JSON con las siguientes columnas: <code>id, createdAt, email, enabled, latitud, longitude, name, phone, photo, usertype, companyId, city, country</code>.
       * @apiVersion 1.0.0
       * @apiParam {String}       Access-Token  El método requiere Token en el Header de la petición
       * @apiParam {Number}       id              un numero entero (unique ID - PrimaryKey) para leer en la tabla
       * @apiName  GetUser
       * @apiGroup Usuarios
       *
       * @apiExample {get} Ejemplo de Solicitud:
       *                      http://toolvendor-beecode.rhcloud.com/rest/user/5
       *
       * @apiUse  RespuestaSuccessListar
       */

      /**
       * @api         {put}           /user/:id   Editar
       * @apiVersion 1.0.0
       * @apiName     EditUsuario
       * @apiGroup    Usuarios
       *
       * @apiExample {put} Ejemplo de Solicitud:
       *                      http://toolvendor-beecode.rhcloud.com/rest/user/5
       *
       * @apiDescription  Este método actualiza los datos de un Usuario, requiere crear un objeto JSON con los parametros a anexar en la petición.
       *                  Cualquiera de los parámetros no previstos serán dejados sin cambios. Las columnas a actualizar son <code>id, createdAt,
       *                  email, enabled, latitud, longitude, name, phone, photo, usertype, companyId, city, country</code>. Tenga en cuenta que
       *                  el atributo <code>id, companyId</code> no es editable. La solicitud retorna un JSON con la
       *                  informacion con <code>status 200</code> en caso de tener exito, de lo contrario retorna <code>status 400</code>.
       *                  Para mas informacion vease los ejemplos a continuación.
       *
       * @apiParam    {String}     Access-Token  El método requiere Token en el Header de la petición
       * @apiParam    {Number}     id           Numero entero (unique ID - PrimaryKey) para actualizar en el modelo.
       * @apiParam    {String}     companyId    Id de la compañia del usuario
       * @apiParam    {String}     [email]        El email del usuario.
       * @apiParam    {String}     [enabled]      El estatus del usuario si esta activo o inactivo para usar la plataforma.
       * @apiParam    {String}     [latitud]      Numero de Latitud de geolocalización GPS del usuario
       * @apiParam    {String}     [longitude]    Numero de Longitude de geolocalización GPS del usuario
       * @apiParam    {String}     [name]         Nombre del usuario
       * @apiParam    {String}     [phone]        Numero de telefono del usuario
       * @apiParam    {String}     [photo]        URL de la fotogradía del usuario
       * @apiParam    {String}     [usertype]     Objeto[UserType] que indica el tipo de usuario
       * @apiParam    {String}     [city]         Objeto[City] que indica la ciudad y pais del usuario
       *
       *
       *
       * @apiUse      RespuestaSuccessInsertarEditar
       * @apiUse      ErrorAlEditar
       */

      /**
       * @api         {delete}        /user/:id   Borrar
       * @apiVersion 1.0.0
       * @apiGroup    Usuarios
       *
       * @apiExample {delete} Ejemplo de Solicitud:
       *                      http://toolvendor-beecode.rhcloud.com/rest/user/5
       *
       * @apiDescription  Este método borra un usuario de la compañia. Solo es posible suprimir el usuario, si ésta no ha sido vinculado a una Visita o una Orden.
       *                  Para eliminarlo, debe indicar el <code>id</code> del Usuario a borrar. La solicitud devuelve un objeto con un
       *                  parámetro <code>success=true</code> en caso de éxito. De lo contrario, esta llamada devuelve un error ( <code>success=false</code> )
       . Para mas información vease los ejemplos a continuación.
       *
       * @apiParam    {String}        Access-Token  El método requiere Token en el Header de la petición
       *
       * @apiParam    {String}        id              El <code>ID</code> de la Categoria a borrar.
       *
       * @apiName     BorraUsuario
       *
       * @apiUse      SuccessAlBorrar
       *
       * @apiUse      ErrorAlBorrar
       */

       /**
        * @api      {get}          /user/:id/contact      Contactos
        * @apiVersion 1.0.0
        * @apiName  ContactByUser
        * @apiGroup Usuarios
        *
        * @apiExample {get} Ejemplo de Solicitud:
        *                      http://toolvendor-beecode.rhcloud.com/rest/user/1/contact
        *
        * @apiDescription  Devuelve una lista de todas los contactos del usuario.
        *                  los contactos están ordenados por el mismo orden en que estan guardados en el celular.
        *                  Las columnas disponibles a mostrar son <code>id, userId, name, phone, phone2, email</code>
        *
        * @apiParam {String}       Access-Token  El método requiere Token en el Header de la petición
        *
        * @apiParam    {String}        id              El <code>ID</code> del usuario.
        *
        * @apiUse  RespuestaSuccessListar
        */

        /**
         * @api      {get}          /user/:id/calls      Llamadas
         * @apiVersion 1.0.0
         * @apiName  CallsByUser
         * @apiGroup Usuarios
         *
         * @apiExample {get} Ejemplo de Solicitud:
         *                      http://toolvendor-beecode.rhcloud.com/rest/user/1/calls
         *
         * @apiDescription  Devuelve una lista de todas las llamadas del usuario.
         *                  las llamadas de los usuarios estan ordenadas de forma descendiente.
         *                  Las columnas disponibles a mostrar son <code>id, userId, name, phone </code>
         *
         * @apiParam {String}       Access-Token  El método requiere Token en el Header de la petición
         *
         * @apiParam    {String}        id              El <code>ID</code> del usuario.
         *
         * @apiUse  RespuestaSuccessListar
         */

         /**
          * @api      {get}          /user/:id/customer      Clientes por Usuario
          * @apiVersion 1.0.0
          * @apiName  CustomerByUser
          * @apiGroup Usuarios
          *
          * @apiExample {get} Ejemplo de Solicitud:
          *                      http://toolvendor-beecode.rhcloud.com/rest/user/1/customer
          *
          * @apiDescription  Devuelve una lista de todos los clientes del usuario.
          *                  los clientes del usuario estan ordenados de forma alfabetica.
          *                  Las columnas disponibles a mostrar son <code>id, createdAt, companyName,
          *                  contactName, contactPhone, contactEmail, building, street, postalCode, reference, latitud, longitud, userId, name, phone, companyId, city, country </code>
          *
          * @apiParam {String}       Access-Token  El método requiere Token en el Header de la petición
          *
          * @apiParam    {String}        id              El <code>ID</code> del usuario.
          *
          * @apiUse  RespuestaSuccessListar
          */

          /**
           * @api      {get}          /user/:id/visit      Vistas por Usuario
           * @apiVersion 1.0.0
           * @apiGroup Usuarios
           *
           * @apiExample {get} Ejemplo de Solicitud:
           *                      http://toolvendor-beecode.rhcloud.com/rest/user/1/visit
           *
           * @apiDescription  Devuelve una lista de todas las visitas del usuario.
           *                  las visitas del usuario estan ordenados de forma descendente.
           *                  Las columnas disponibles a mostrar son <code>id, userId, customer,
           *                  companyId, visitType, createdAt, scheduledDate, checkin, checkout, firm, comment, reason </code>
           * @apiName  VisitByUser
           * @apiGroup Usuarios
           *
           * @apiParam {String}       Access-Token  El método requiere Token en el Header de la petición
           *
           * @apiParam    {String}        id              El <code>ID</code> del usuario.
           *
           * @apiUse  RespuestaSuccessListar
           */


// ------------------------------------------------------------------------------------------
// C A T E G O R I A S.
// ------------------------------------------------------------------------------------------

    /**
     * @api      {get}          /category      Listar
     * @apiVersion 1.0.0
     * @apiName  GetAll
     * @apiGroup Categoria
     *
     * @apiExample {get} Ejemplo de Solicitud:
     *                      http://toolvendor-beecode.rhcloud.com/rest/category
     *
     * @apiDescription  Devuelve una lista de todas las Categorias. Las Categorias se devuelven ordenadas por fecha de creación,
     *                  las Categorias más recientemente creadas aparecen en primer lugar. Las columnas disponibles a mostrar son
     *                  <code>id, companyId, category</code>
     * @apiName  GetAll
     * @apiGroup Categoria
     *
     * @apiParam    {String}        Access-Token  El método requiere Token en el Header de la petición
     *
     *
     * @apiUse  RespuestaSuccessListar
     */

    /**
     * @api      {post}         /category      Crear
     * @apiVersion 1.0.0
     * @apiName  CreateCategoria
     * @apiGroup Categoria
     *
     * @apiExample {post} Ejemplo de Solicitud:
     *                      http://toolvendor-beecode.rhcloud.com/rest/category
     *
     * @apiDescription  Crea un nuevo objeto Categoria. Las columnas guardar son <code>companyId, category</code>.
     *                   La solicitud retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito,
     *                   de lo contrario devuelve <code>status 400</code>. Para mas informacion vease los ejemplos a continuación.
     *
     * @apiParam    {String}     Access-Token  El método requiere Token en el Header de la petición
     *
     * @apiParam    {String}     companyId   Id de la compañia del usuario.
     * @apiParam    {String}     category    La descripción de la Categoria que se va a mostrar al usuario.
     *
     * @apiUse  RespuestaSuccessInsertarEditar
     */

    /**
     * @api      {get}          /category/:id   Leer
     * @apiDescription  Recupera los detalles de una Categoria existente. Suministrar el <code>id</code> de Categoria para devolver la
     *                  información de la Categoria correspondiente. La respuesta devuelve un conjunto de variables, la variable
     *                  <code>result</code> contiene JSON con las siguientes columnas: <code>id, companyId, category</code>.
     * @apiVersion 1.0.0
     * @apiParam {Number}       id              un numero entero (unique ID - PrimaryKey) para leer en la tabla
     * @apiName  GetCategoria
     * @apiGroup Categoria
     *
     * @apiExample {get} Ejemplo de Solicitud:
     *                      http://toolvendor-beecode.rhcloud.com/rest/category/91
     *
     * @apiParam    {String}        Access-Token  El método requiere Token en el Header de la petición
     *
     * @apiUse  RespuestaSuccessListar
     */

    /**
     * @api         {put}           /category/:id   Editar
     * @apiVersion 1.0.0
     * @apiName     EditCategoria
     * @apiGroup    Categoria
     *
     * @apiExample {put} Ejemplo de Solicitud:
     *                      http://toolvendor-beecode.rhcloud.com/rest/category/91
     *
     * @apiDescription  Actualiza los detalles de la Categoria específica, mediante el establecimiento de los valores de los parámetros pasados.
     *                  Cualquiera de los parámetros no previstos serán dejados sin cambios. Las columnas a actualizar son <code>name, description,
     *                  photo</code>. Tenga en cuenta que el atributo <code>id</code> no es editable. La solicitud retorna un JSON con la
     *                  informacion con <code>status 200</code> en caso de tener exito, de lo contrario devuelve <code>status 400</code>.
     *                  Para mas informacion vease los ejemplos a continuación.
     *
     * @apiParam    {String}     Access-Token  El método requiere Token en el Header de la petición
     *
     * @apiParam    {Number}     id un numero entero (unique ID - PrimaryKey) para actualizar en el modelo.
     * @apiParam    {String}     companyId     ID de la compañia del usuario.
     * @apiParam    {String}     [category]    La descripción de la Categoria que se va a actualizar.
     *
     * @apiUse      RespuestaSuccessInsertarEditar
     * @apiUse      ErrorAlEditar
     */

    /**
     * @api         {delete}        /category/:id   Borrar
     * @apiVersion 1.0.0
     * @apiGroup    Categoria
     *
     * @apiExample {delete} Ejemplo de Solicitud:
     *                      http://toolvendor-beecode.rhcloud.com/rest/category/91
     *
     * @apiDescription  Permite borrar una Categoria. Solo es posible suprimir la Categoria, si ésta no ha sido vinculada a un Establecimiento.
     *                  Para eliminarla, debe indicar el <code>id</code> de la Categoria a borrar. La solicitud devuelve un objeto con un
     *                  parámetro <code>success=true</code> en caso de éxito. De lo contrario, esta llamada devuelve un error ( <code>success=false</code> )
     . Para mas información vease los ejemplos a continuación.
     *
     * @apiParam    {String}        Access-Token  El método requiere Token en el Header de la petición
     *
     * @apiParam    {String}        id              El <code>ID</code> de la Categoria a borrar.
     *
     * @apiName     BorraCategoria
     *
     * @apiUse      SuccessAlBorrar
     *
     * @apiUse      ErrorAlBorrar
     */


// ------------------------------------------------------------------------------------------
// U S E R  T Y P E.
// ------------------------------------------------------------------------------------------

/**
 * @api      {get}          /usertype/      Listar
 * @apiVersion 1.0.0
 * @apiName  GetAll
 * @apiGroup Tipos de Usuario
 *
 * @apiExample {get} Ejemplo de Solicitud:
 *                      http://toolvendor-beecode.rhcloud.com/rest/usertype/
 *
 * @apiDescription  Devuelve una lista de todos los Departamentos. Las Departamentos se devuelven ordenados por fecha de creación,
 *                  los Departamentos más recientemente creados aparecen en primer lugar. Las columnas disponibles a mostrar son
 *                  <code>id, name, phone, fax, address, lat, lng, photo, website</code>
 * @apiName  GetAll
 * @apiGroup Tipos de Usuario
 *
 * @apiUse  RespuestaSuccessListar
 */

/**
 * @api      {post}         /usertype/      Crear
 * @apiVersion 1.0.0
 * @apiName  CreateUserType
 * @apiGroup Tipos de Usuario
 *
 * @apiExample {post} Ejemplo de Solicitud:
 *                      http://toolvendor-beecode.rhcloud.com/rest/usertype
 *
 * @apiDescription  Crea un nuevo objeto UserType. Las columnas guardar son <code>description, type, companyId</code>.
 *                   La solicitud retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito,
 *                   de lo contrario devuelve <code>status 400</code>. Para mas informacion vease los ejemplos a continuación.
 *
 * @apiParam    {String}     [companyId]    ID de la compañia del usuario.
 * @apiParam    {String}     [description]  La descripción del tipo de usuario.
 * @apiParam    {String}     [type]         El tipo de usuario reconocido por el sistema. 'A' = ADMIN, 'M' = Movil
 *
 * @apiUse  RespuestaSuccessInsertarEditar
 */

/**
 * @api      {get}          /usertype/:id   Leer
 * @apiDescription  Retorna los datos de un UserType existente. Suministrar el <code>id</code> del UserType
 *                  para recuperar los datos correspondientes. La respuesta devuelve un conjunto de variables, la variable
 *                  <code>result</code> contiene JSON con las siguientes columnas: <code>description, type, companyId</code>.
 * @apiVersion 1.0.0
 * @apiParam {Number}       id              El <code>id</code> del Departamento que se está leyendo.
 * @apiName  GetUserType
 * @apiGroup Tipos de Usuario
 *
 * @apiExample {get} Ejemplo de Solicitud:
 *                      http://toolvendor-beecode.rhcloud.com/rest/usertype/5
 *
 * @apiUse  RespuestaSuccessListar
 */

/**
 * @api         {put}           /usertype/:id   Editar
 * @apiVersion 1.0.0
 * @apiName     EditDepartamento
 * @apiGroup    Tipos de Usuario
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      http://toolvendor-beecode.rhcloud.com/rest/usertype/5
 *
 * @apiDescription  Actualiza los detalles del UserType específico, mediante el identificación de los valores de los parámetros pasados.
 *                  Cualquiera de los parámetros no previstos serán dejados sin cambios. Las columnas a actualizar son <code>description, type, companyId</code>.
 *                  Tenga en cuenta que el atributo <code>id</code> no es editable. La solicitud
 *                  retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito, de lo contrario devuelve <code>status 400</code>.
 *                  Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}     [id]            El valor <code>id</code> del UserType.
 * @apiParam    {String}     companyId       ID de la compañia del usuario.
 * @apiParam    {String}     description     La descripción del tipo de usuario.
 * @apiParam    {String}     type            El tipo de usuario reconocido por el sistema. 'A' = ADMIN, 'M' = Movil
 *
 * @apiUse      RespuestaSuccessInsertarEditar
 * @apiUse      ErrorAlEditar
 */

/**
 * @api         {delete}        /usertype/:id   Borrar
 * @apiVersion 1.0.0
 * @apiGroup    Tipos de Usuario
 *
 * @apiExample {delete} Ejemplo de Solicitud:
 *                      http://toolvendor-beecode.rhcloud.com/rest/usertype/5
 *
 * @apiDescription  Permite borrar un UserType. Solo es posible suprimir el tipo de usuario, si éste no ha sido vinculado con un <code>Usuario</code>.
 *                  Para eliminarlo, debe indicar el <code>id</code> del UserType a borrar. La petición retorna un objeto con un
 *                  parámetro <code>success=true</code> en caso de éxito. De lo contrario retorna ( <code>success=false</code> )
 . Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}        [id]              El <code>id</code> del Tipo de Usuario que se va a borrar.
 *
 * @apiName     BorraUserType
 *
 * @apiUse      SuccessAlBorrar
 *
 * @apiUse      ErrorAlBorrar
 */


 // ------------------------------------------------------------------------------------------
 // U P L O A D   F I L E.
 // ------------------------------------------------------------------------------------------
 /**
  * @api      {post}          /upload/      Upload
  * @apiVersion 1.0.0
  * @apiName  Upload
  * @apiGroup UploadFile
  *
  * @apiExample {post} Ejemplo de Solicitud:
  *                      http://toolvendor-beecode.rhcloud.com/upload
  *
  * @apiDescription  Este metodo POST sube un archivo al repositorio Amazon S3 de Toolvendor,
  *                  Se puede subir casi cualquier tipo de archivo de imagen o video. El servicio despues de hacer el Upload retorna
  *                  una respuesta con los datos de la ruta del archivo <code>filesize, filename, url, success</code>
  *
  * @apiParam    {String}    file1         el valor <code>file1</code> es un parametro de tipo file que contiene la el archivo a subir al repositorio.
  *
  *
  * @apiUse  RespuestaSuccessListar
  */


// ------------------------------------------------------------------------------------------
// P R O D U C T S.
// ------------------------------------------------------------------------------------------

/**
 * @api      {get}          /product/      Listar
 * @apiVersion 1.0.0
 * @apiName  GetAll
 * @apiGroup Producto
 *
 * @apiExample {get} Ejemplo de Solicitud:
 *                      http://toolvendor-beecode.rhcloud.com/rest/product
 *
 * @apiDescription  Devuelve una lista de todos los Productos. Las Productos se devuelven ordenados por fecha de creación,
 *                  los Productos más recientemente creados aparecen en primer lugar. Las columnas disponibles a mostrar son
 *                  <code>id, barcode, name, description, detail, enabled, price, tax, photo, brand, family, category, presentation, productType</code>
 * @apiName  GetAll
 * @apiGroup Producto
 *
 * @apiUse  RespuestaSuccessListar
 */

/**
 * @api      {post}         /product/      Crear
 * @apiVersion 1.0.0
 * @apiName  CreateProducto
 * @apiGroup Producto
 *
 * @apiExample {post} Ejemplo de Solicitud:
 *                      http://toolvendor-beecode.rhcloud.com/rest/product/
 *
 * @apiDescription  Crea un nuevo objeto Producto. Las columnas guardar son <code>barcode, name, description, detail, enabled, price, tax, photo, brand, family, category, presentation, productType</code>.
 *                   La solicitud retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito,
 *                   de lo contrario devuelve <code>status 400</code>. Para mas informacion vease los ejemplos a continuación.
 *
 * @apiParam    {String}    barcode         el valor <code>id</code> del Departamento al cual pertenece el Producto.
 * @apiParam    {String}    name            Nombre del Producto que se va a mostrar al usuario.
 * @apiParam    {String}    [description]   La descripción del Producto que se va a mostrar al usuario.
 * @apiParam    {String}    [detail]        Detalles del Producto.
 * @apiParam    {String}    [enabled]       Indica si el producto esta activo.
 * @apiParam    {Decimal}   [price]         Precio de venta del Producto para mostrar. Ej. $57.99
 * @apiParam    {Decimal}   [tax]           Precio de tax del Producto para mostrar. Ej. $7.99
 * @apiParam    {String}    [photo]         En esta columna se almacena el path de ubicación de la imagen a mostrar del producto.
 * @apiParam    {String}    [brand]         Marca del Producto.
 * @apiParam    {String}    [family]        Familia del producto.
 * @apiParam    {String}    [category]      Categoria del Producto.
 * @apiParam    {String}    [presentation]  Presentacion del Producto.
 * @apiParam    {String}    [productType]   Tipo del Producto.
 *
 * @apiUse  RespuestaSuccessInsertarEditar
 */

/**
 * @api      {get}          /product/:id   Leer
 * @apiDescription  Recupera los detalles de un Producto existente. Suministrar el <code>id</code> del Producto para devolver la
 *                  información de la Producto correspondiente. La respuesta devuelve un conjunto de variables, la variable
 *                  <code>result</code> contiene JSON con las siguientes columnas: <code>id, barcode, name, description, detail, enabled, price, tax, photo, brand, family, category, presentation, productType</code>.
 * @apiVersion 1.0.0
 * @apiParam {Number}       id              El <code>id</code> del Producto que se está leyendo.
 * @apiName  GetProducto
 * @apiGroup Producto
 *
 * @apiExample {get} Ejemplo de Solicitud:
 *                      http://toolvendor-beecode.rhcloud.com/rest/product/20
 *
 * @apiUse  RespuestaSuccessListar
 */

/**
 * @api         {put}           /product/:id   Editar
 * @apiVersion 1.0.0
 * @apiName     EditProducto
 * @apiGroup    Producto
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      http://toolvendor-beecode.rhcloud.com/rest/product/20
 *
 * @apiDescription  Actualiza los detalles del Producto específico, mediante el identificación de los valores de los parámetros pasados.
 *                  Cualquiera de los parámetros no previstos serán dejados sin cambios. Las columnas a actualizar son
 *                  <code>id, barcode, name, description, detail, enabled, price, tax, photo, brand, family, category, presentation, productType</code>.
 *                  Tenga en cuenta que el atributo <code>id</code> no es editable. La solicitud
 *                  retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito, de lo contrario devuelve <code>status 400</code>.
 *                  Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}    barcode         el valor <code>id</code> del Departamento al cual pertenece el Producto.
 * @apiParam    {String}    name            Nombre del Producto que se va a mostrar al usuario.
 * @apiParam    {String}    [description]   La descripción del Producto que se va a mostrar al usuario.
 * @apiParam    {String}    [detail]        Detalles del Producto.
 * @apiParam    {String}    [enabled]       Indica si el producto esta activo.
 * @apiParam    {Decimal}   [price]         Precio de venta del Producto para mostrar. Ej. $57.99
 * @apiParam    {Decimal}   [tax]           Precio de tax del Producto para mostrar. Ej. $7.99
 * @apiParam    {String}    [photo]         En esta columna se almacena el path de ubicación de la imagen a mostrar del producto.
 * @apiParam    {String}    [brand]         Marca del Producto.
 * @apiParam    {String}    [family]        Familia del producto.
 * @apiParam    {String}    [category]      Categoria del Producto.
 * @apiParam    {String}    [presentation]  Presentacion del Producto.
 * @apiParam    {String}    [productType]   Tipo del Producto.
 *
 * @apiUse      RespuestaSuccessInsertarEditar
 * @apiUse      ErrorAlEditar
 */

/**
 * @api         {delete}        /product/:id   Borrar
 * @apiVersion 1.0.0
 * @apiGroup    Producto
 *
 * @apiExample {delete} Ejemplo de Solicitud:
 *                      http://toolvendor-beecode.rhcloud.com/rest/product/2
 *
 * @apiDescription  Permite borrar un Producto. Solo es posible suprimir el Producto, si éste no ha sido vinculado con una <code>Orden, Cotizacion</code>.
 *                  Para eliminarlo, debe indicar el <code>id</code> del Producto a borrar. La solicitud devuelve un objeto con un
 *                  parámetro <code>success=true</code> en caso de éxito. De lo contrario, esta llamada devuelve un error ( <code>success=false</code> )
 . Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}        id              El <code>id</code> del Producto que se va a borrar.
 *
 * @apiName     BorraProducto
 *
 * @apiUse      SuccessAlBorrar
 *
 * @apiUse      ErrorAlBorrar
 */






// ------------------------------------------------------------------------------------------
// C U S T O M E R S
// ------------------------------------------------------------------------------------------

/**
 * @api      {get}          /customer/      Listar
 * @apiVersion 1.0.0
 * @apiName  GetAll
 * @apiGroup Cliente
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      http://toolvendor-beecode.rhcloud.com/rest/customer/
 *
 * @apiDescription  Retorna una lista de todos los Clientes asignados al usuario. Los Clientes se devuelven ordenados por fecha de creación,
 *                  los Clientes más recientemente creados aparecen en primer lugar. Las columnas disponibles a mostrar son
     *                  <code>userId, createdAt, companyName, contactName, contactPhone, contactEmail, active, building, street, postalCode, reference, latitud, longitude, companyId, city, country</code>
 * @apiName  GetAll
 * @apiGroup Cliente
 *
 * @apiUse  RespuestaSuccessListar
 */

/**
 * @api      {post}         /customer/      Crear
 * @apiVersion 1.0.0
 * @apiName  CreateCliente
 * @apiGroup Cliente
 *
 * @apiExample {post} Ejemplo de Solicitud:
 *                      http://toolvendor-beecode.rhcloud.com/rest/customer/
 *
 * @apiDescription   Crea un nuevo objeto Cliente. Las columnas guardar son <code>userId, createdAt, companyName, contactName, contactPhone, contactEmail, active, building, street, postalCode, reference, latitud, longitude, companyId, city, country</code>.
 *                   La solicitud retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito,
 *                   de lo contrario devuelve <code>status 400</code>. Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}    userId            Id del usuario a asignar el cliente.
 * @apiParam    {String}    companyName       Nombre de la empresa cliente.
 * @apiParam    {String}    contactName       Nombre completo del representante de la empresa.
 * @apiParam    {String}    contactPhone      Número de telefono del representante de la empresa.
 * @apiParam    {String}    contactEmail      Direccion de correo electronico del representante.
 * @apiParam    {String}    [active]          Active es una variable tipo Bandera que indica si el cliente está activo o inactivo en el sistema.
 * @apiParam    {String}    [building]        En esta columna se almacena el nombre de eficio, casa u oficina de la empresa.
 * @apiParam    {String}    street            En esta columna se almacena la direccion de las calles de la empresa.
 * @apiParam    {String}    reference         Una referencia de la direccion de la empresa.
 * @apiParam    {String}    latitud           Latitud de la ubicación del cliente.
 * @apiParam    {String}    longitude         En esta columna se almacena la direccion de las calles de la empresa.
 * @apiParam    {String}    companyId         Numero telefonico de contacto al cliente.
 * @apiParam    {String}    city              Objeto City con los datos de la ciudad del cliente.
 * @apiParam    {String}    [postalCode]      Codigo Postal del pais del cliente.
 *
 * @apiUse  RespuestaSuccessInsertarEditar
 */

/**
 * @api      {get}          /customer/:id   Leer
 * @apiDescription  Recupera los detalles de un Cliente existente. Suministrar el <code>id</code> del Cliente para devolver la
 *                  información del Cliente correspondiente. La respuesta devuelve un conjunto de variables, la variable
 *                  <code>result</code> contiene JSON con las siguientes columnas: <br>
 *                      <code>userId, createdAt, companyName, contactName, contactPhone, contactEmail, active, building, street, postalCode, reference, latitud, longitude, companyId, city, country</code>.
 * @apiVersion 1.0.0
 * @apiParam {Number}       id              El <code>id</code> del Cliente que se está leyendo.
 * @apiName  GetCliente
 * @apiGroup Cliente
 *
 * @apiExample {get} Ejemplo de Solicitud:
 *                      http://toolvendor-beecode.rhcloud.com/rest/customer/105
 *
 * @apiUse  RespuestaSuccessListar
 */

/**
 * @api         {put}           /customer/:id   Editar
 * @apiVersion 1.0.0
 * @apiName     EditCliente
 * @apiGroup    Cliente
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      http://toolvendor-beecode.rhcloud.com/rest/customer/135e656e6cda8640820350816ab59f2d
 *
 * @apiDescription  Actualiza los detalles del Cliente específico, mediante la identificación de los valores de los parámetros suministrados.
 *                  Cualquiera de los parámetros no previstos serán dejados sin cambios. Las columnas a actualizar son<br>
 *                  <code>first_name, last_name, email, password, photo, phone, security_question, answer</code>.<br>
 *                  Tenga en cuenta que el atributo <code>id</code> no es editable. La solicitud
 *                  retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito, de lo contrario devuelve <code>status 400</code>.
 *                  Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}    id                  Id del cliente a modificar.
 * @apiParam    {String}    userId              Id del usuario a asignar el cliente.
 * @apiParam    {String}    [companyName]       Nombre de la empresa cliente.
 * @apiParam    {String}    [contactName]       Nombre completo del representante de la empresa.
 * @apiParam    {String}    [contactPhone]      Número de telefono del representante de la empresa.
 * @apiParam    {String}    [contactEmail]      Direccion de correo electronico del representante.
 * @apiParam    {String}    [active]            Active es una variable tipo Bandera que indica si el cliente está activo o inactivo en el sistema.
 * @apiParam    {String}    [building]          En esta columna se almacena el nombre de eficio, casa u oficina de la empresa.
 * @apiParam    {String}    [street]            En esta columna se almacena la direccion de las calles de la empresa.
 * @apiParam    {String}    [reference]         Una referencia de la direccion de la empresa.
 * @apiParam    {String}    [latitud]           Latitud de la ubicación del cliente.
 * @apiParam    {String}    [longitude]         En esta columna se almacena la direccion de las calles de la empresa.
 * @apiParam    {String}    [companyId]         Numero telefonico de contacto al cliente.
 * @apiParam    {String}    [city]              Objeto City con los datos de la ciudad del cliente.
 * @apiParam    {String}    [postalCode]        Codigo Postal del pais del cliente.
 *
 * @apiUse      RespuestaSuccessInsertarEditar
 * @apiUse      ErrorAlEditar
 */

/**
 * @api         {delete}        /customer/:id   Borrar
 * @apiVersion 1.0.0
 * @apiGroup    Cliente
 *
 * @apiExample {delete} Ejemplo de Solicitud:
 *                      http://toolvendor-beecode.rhcloud.com/rest/customer/105
 *
 * @apiDescription  Permite borrar un Cliente. Solo es posible suprimir el Cliente, si éste no ha sido vinculado con un(a) <code>Usuario, Visita, Orden</code>.
 *                  Para eliminarlo, debe indicar el <code>id</code> del Cliente a borrar. La solicitud devuelve un objeto con un
 *                  parámetro <code>success=true</code> en caso de éxito. De lo contrario, esta llamada devuelve un error ( <code>success=false</code> )
 . Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}        id              El <code>id</code> del Cliente que se va a borrar.
 *
 * @apiName     BorraCliente
 *
 * @apiUse      SuccessAlBorrar
 *
 * @apiUse      ErrorAlBorrar
 */

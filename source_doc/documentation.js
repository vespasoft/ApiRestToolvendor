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
 *       "message": "Ocurrio un error al intentar borrar",
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
 *       "message": "Registro Borrado"
 *     }
 */


/**
 * @apiDefine RespuestaSuccessListar
 *
 * @apiSuccess    {Boolean}     success     devuelve true si la solicitud tuvo exito.
 * @apiSuccess    {Integer}     total       cantidad de registros devueltos.
 * @apiSuccess    {String}      mesasge     mensaje descriptivo de la informacion devuelta desde el servidor.
 * @apiSuccess    {Object[]}    result      contiene un arreglo de objetos en formato json, contenedor de todos los registros.
 *
 * @apiSuccessExample Success-Response:
 *     HTTP/1.1 200 OK
 *     {
 *       "success" : true,
 *       "total"   : 5,
 *       "message" : "",
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
 * @apiDefine ErrorAlEditar
 *
 * @apiError    success:false   ocurrio un error la ejecucion de la solicitud de borrado
 * @apiError    message         mensaje descriptivo del error devuelto desde el servidor
 *
 * @apiErrorExample Error-Response:
 *     HTTP/1.1 404 Not Found
 *     {
 *       "success": false,
 *       "message": "No se encontro el id X",
 *       "result": null
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
 *       "message" : "la solicitud se realizo con exito",
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
// C A T E G O R I A S.
// ------------------------------------------------------------------------------------------

    /**
     * @api      {get}          /category/      Listar
     * @apiVersion 1.0.0
     * @apiName  GetAll
     * @apiGroup Categoria
     *
     * @apiExample {put} Ejemplo de Solicitud:
     *                      https://apirestfm.herokuapp.com/api/category/
     *
     * @apiDescription  Devuelve una lista de todas las Categorias. Las Categorias se devuelven ordenadas por fecha de creación,
     *                  las Categorias más recientemente creadas aparecen en primer lugar. Las columnas disponibles a mostrar son
     *                  <code>id, name, description, photo</code>
     * @apiName  GetAll
     * @apiGroup Categoria
     *
     * @apiUse  RespuestaSuccessListar
     */

    /**
     * @api      {post}         /category/      Crear
     * @apiVersion 1.0.0
     * @apiName  CreateCategoria
     * @apiGroup Categoria
     *
     * @apiExample {put} Ejemplo de Solicitud:
     *                      https://apirestfm.herokuapp.com/api/category/
     *
     * @apiDescription  Crea un nuevo objeto Categoria. Las columnas guardar son <code>name, description, photo</code>.
     *                   La solicitud retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito,
     *                   de lo contrario devuelve <code>status 400</code>. Para mas informacion vease los ejemplos a continuación.
     *
     * @apiParam    {String}     name   Nombre de la Categoria que se va a mostrar al usuario.
     * @apiParam    {String}     [description]  La descripción de la Categoria que se va a mostrar al usuario.
     * @apiParam    {String}     [photo]  En esta columna se almacena el path de ubicación de la imagen a mostrar de la categoria.
     *
     * @apiUse  RespuestaSuccessInsertarEditar
     */

    /**
     * @api      {get}          /category/:id   Leer
     * @apiDescription  Recupera los detalles de una Categoria existente. Suministrar el <code>id</code> de Categoria para devolver la
     *                  información de la Categoria correspondiente. La respuesta devuelve un conjunto de variables, la variable
     *                  <code>result</code> contiene JSON con las siguientes columnas: <code>id, name, description, photo</code> y una
     *                  coleccion de Establecimientos (<code>stores</code>).
     * @apiVersion 1.0.0
     * @apiParam {Number}       id              un numero entero (unique ID - PrimaryKey) para leer en la tabla
     * @apiName  GetCategoria
     * @apiGroup Categoria
     *
     * @apiExample {put} Ejemplo de Solicitud:
     *                      https://apirestfm.herokuapp.com/api/category/135e656e6cda8640820350816ab59f2d
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
     *                      https://apirestfm.herokuapp.com/api/category/135e656e6cda8640820350816ab59f2d
     *
     * @apiDescription  Actualiza los detalles de la Categoria específica, mediante el establecimiento de los valores de los parámetros pasados.
     *                  Cualquiera de los parámetros no previstos serán dejados sin cambios. Las columnas a actualizar son <code>name, description,
     *                  photo</code>. Tenga en cuenta que el atributo <code>id</code> no es editable. La solicitud retorna un JSON con la
     *                  informacion con <code>status 200</code> en caso de tener exito, de lo contrario devuelve <code>status 400</code>.
     *                  Para mas informacion vease los ejemplos a continuación.
     *
     * @apiParam    {Number}     id     un numero entero (unique ID - PrimaryKey) para actualizar en el modelo.
     * @apiParam    {String}     name   Nombre de la Categoria que se va a mostrar al usuario.
     * @apiParam    {String}     [description]  La descripción de la Categoria que se va a mostrar al usuario.
     * @apiParam    {String}     [photo]  En esta columna se almacena el path de ubicación de la imagen a mostrar de la categoria.
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
     *                      https://apirestfm.herokuapp.com/api/category/135e656e6cda8640820350816ab59f2d
     *
     * @apiDescription  Permite borrar una Categoria. Solo es posible suprimir la Categoria, si ésta no ha sido vinculada a un Establecimiento.
     *                  Para eliminarla, debe indicar el <code>id</code> de la Categoria a borrar. La solicitud devuelve un objeto con un
     *                  parámetro <code>success=true</code> en caso de éxito. De lo contrario, esta llamada devuelve un error ( <code>success=false</code> )
     . Para mas información vease los ejemplos a continuación.
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
// S T O R E.
// ------------------------------------------------------------------------------------------

/**
 * @api      {get}          /store/      Listar
 * @apiVersion 1.0.0
 * @apiName  GetAll
 * @apiGroup Establecimiento
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/store/
 *
 * @apiDescription  Devuelve una lista de todos los Establecimientos. Las Establecimientos se devuelven ordenados por fecha de creación,
 *                  las Establecimientos más recientemente creados aparecen en primer lugar. Las columnas disponibles a mostrar son
 *                  <code>id, name, phone, fax, address, lat, lng, photo, website</code>
 * @apiName  GetAll
 * @apiGroup Establecimiento
 *
 * @apiUse  RespuestaSuccessListar
 */

/**
 * @api      {post}         /store/      Crear
 * @apiVersion 1.0.0
 * @apiName  CreateEstablecimiento
 * @apiGroup Establecimiento
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/store/
 *
 * @apiDescription  Crea un nuevo objeto Establecimiento. Las columnas guardar son <code>name, phone, fax, address, lat, lng, photo, website</code>.
 *                   La solicitud retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito,
 *                   de lo contrario devuelve <code>status 400</code>. Para mas informacion vease los ejemplos a continuación.
 *
 * @apiParam    {String}    category_id el valor <code>id</code> de la categoria a la cual pertenece el Establecimiento.
 * @apiParam    {String}    name        Nombre de la Establecimiento que se va a mostrar al usuario.
 * @apiParam    {String}    [phone]     numero telefónico del establecimiento.
 * @apiParam    {String}    [fax]       numero de fax del establecimiento.
 * @apiParam    {String}    [address]   dirección de ubicación del establecimiento.
 * @apiParam    {String}    [photo]     En esta columna se almacena el path de ubicación de la imagen a mostrar del establecimiento.
 * @apiParam    {Float}     [lat]       coordenada latitud del establecimiento expresada en medida angular que varía entre los 0° del ecuador
 *                                          hasta los 90°. Ej. 8.97737
 * @apiParam    {Float}     [lng]       coordenada longitud del establecimiento expresada en medida angular que varía entre los 0° del ecuador
 *                                          hasta los 180°. Ej. -79.51831
 *
 * @apiUse  RespuestaSuccessInsertarEditar
 */

/**
 * @api      {get}          /store/:id   Leer
 * @apiDescription  Recupera los detalles de un Establecimiento existente. Suministrar el <code>id</code> del Establecimiento para devolver la
 *                  información de la Establecimiento correspondiente. La respuesta devuelve un conjunto de variables, la variable
 *                  <code>result</code> contiene JSON con las siguientes columnas: <code>id, category_id, name, phone, fax, address, lat, lng, photo, website</code>.
 *                  <br>Tambien devuelve una coleccion de Departamentos (<code>departments</code>).
 * @apiVersion 1.0.0
 * @apiParam {Number}       id              El <code>id</code> del Establecimiento que se está leyendo.
 * @apiName  GetEstablecimiento
 * @apiGroup Establecimiento
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/store/135e656e6cda8640820350816ab59f2d
 *
 * @apiUse  RespuestaSuccessListar
 */


/**
 * @api         {put}           /store/:id   Editar
 * @apiVersion 1.0.0
 * @apiName     EditEstablecimiento
 * @apiGroup    Establecimiento
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/store/135e656e6cda8640820350816ab59f2d
 *
 * @apiDescription  Actualiza los detalles del Establecimiento específico, mediante el identificación de los valores de los parámetros pasados.
 *                  Cualquiera de los parámetros no previstos serán dejados sin cambios. Las columnas a actualizar son <code>category_id, name, phone, fax,
 *                  address, lat, lng, photo, website</code>. Tenga en cuenta que el atributo <code>id</code> no es editable. La solicitud
 *                  retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito, de lo contrario devuelve <code>status 400</code>.
 *                  Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {Number}     id     el <code>id</code> del establecimiento que se está actualizando.
 * @apiParam    {Number}     category_id     el valor <code>id</code> de la categoria a la cual pertenece el Establecimiento.
 * @apiParam    {String}    [phone]     numero telefónico del establecimiento.
 * @apiParam    {String}    [fax]       numero de fax del establecimiento.
 * @apiParam    {String}    [address]   dirección de ubicación del establecimiento.
 * @apiParam    {String}    [photo]  En esta columna se almacena el path de ubicación de la imagen a mostrar del establecimiento.
 * @apiParam    {Float}     [lat]       coordenada latitud del establecimiento expresada en medida angular que varía entre los 0° del ecuador
 *                                          hasta los 90°. Ej. 8.97737
 * @apiParam    {Float}     [lng]       coordenada longitud del establecimiento expresada en medida angular que varía entre los 0° del ecuador
 *                                          hasta los 180°. Ej. -79.51831
 *
 * @apiUse      RespuestaSuccessInsertarEditar
 * @apiUse      ErrorAlEditar
 */

/**
 * @api         {delete}        /store/:id   Borrar
 * @apiVersion 1.0.0
 * @apiGroup    Establecimiento
 *
 * @apiExample {delete} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/store/135e656e6cda8640820350816ab59f2d
 *
 * @apiDescription  Permite borrar un Establecimiento. Solo es posible suprimir el Establecimiento, si éste no ha sido vinculado con un <code>Departamento</code>.
 *                  Para eliminarlo, debe indicar el <code>id</code> del Establecimiento a borrar. La solicitud devuelve un objeto con un
 *                  parámetro <code>success=true</code> en caso de éxito. De lo contrario, esta llamada devuelve un error ( <code>success=false</code> )
 . Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}        id              El <code>id</code> del Establecimiento que se va a borrar.
 *
 * @apiName     BorraEstablecimiento
 *
 * @apiUse      SuccessAlBorrar
 *
 * @apiUse      ErrorAlBorrar
 */





// ------------------------------------------------------------------------------------------
// D E P A R T M E N T.
// ------------------------------------------------------------------------------------------

/**
 * @api      {get}          /department/      Listar
 * @apiVersion 1.0.0
 * @apiName  GetAll
 * @apiGroup Departamento
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/department/
 *
 * @apiDescription  Devuelve una lista de todos los Departamentos. Las Departamentos se devuelven ordenados por fecha de creación,
 *                  los Departamentos más recientemente creados aparecen en primer lugar. Las columnas disponibles a mostrar son
 *                  <code>id, name, phone, fax, address, lat, lng, photo, website</code>
 * @apiName  GetAll
 * @apiGroup Departamento
 *
 * @apiUse  RespuestaSuccessListar
 */

/**
 * @api      {post}         /department/      Crear
 * @apiVersion 1.0.0
 * @apiName  CreateDepartamento
 * @apiGroup Departamento
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/department/
 *
 * @apiDescription  Crea un nuevo objeto Departamento. Las columnas guardar son <code>store_id, name, description, photo</code>.
 *                   La solicitud retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito,
 *                   de lo contrario devuelve <code>status 400</code>. Para mas informacion vease los ejemplos a continuación.
 *
 * @apiParam    {String}    store_id el valor <code>id</code> del Establecimiento al cual pertenece el Departamento.
 * @apiParam    {String}     name   Nombre de la Departamento que se va a mostrar al usuario.
 * @apiParam    {String}     [description]  La descripción del Departamento que se va a mostrar al usuario.
 * @apiParam    {String}     [photo]  En esta columna se almacena el path de ubicación de la imagen a mostrar del departamento.
 *
 * @apiUse  RespuestaSuccessInsertarEditar
 */

/**
 * @api      {get}          /department/:id   Leer
 * @apiDescription  Recupera los detalles de un Departamento existente. Suministrar el <code>id</code> del Departamento para devolver la
 *                  información de la Departamento correspondiente. La respuesta devuelve un conjunto de variables, la variable
 *                  <code>result</code> contiene JSON con las siguientes columnas: <code>id, store_id, name, description, photo</code>
 *                  y una colección de Productos (<code>products</code>).
 * @apiVersion 1.0.0
 * @apiParam {Number}       id              El <code>id</code> del Departamento que se está leyendo.
 * @apiName  GetDepartamento
 * @apiGroup Departamento
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/department/135e656e6cda8640820350816ab59f2d
 *
 * @apiUse  RespuestaSuccessListar
 */

/**
 * @api         {put}           /department/:id   Editar
 * @apiVersion 1.0.0
 * @apiName     EditDepartamento
 * @apiGroup    Departamento
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/department/135e656e6cda8640820350816ab59f2d
 *
 * @apiDescription  Actualiza los detalles del Departamento específico, mediante el identificación de los valores de los parámetros pasados.
 *                  Cualquiera de los parámetros no previstos serán dejados sin cambios. Las columnas a actualizar son <code>store_id, name, description, photo</code>.
 *                  Tenga en cuenta que el atributo <code>id</code> no es editable. La solicitud
 *                  retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito, de lo contrario devuelve <code>status 400</code>.
 *                  Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}    store_id el valor <code>id</code> del Establecimiento al cual pertenece el Departamento.
 * @apiParam    {String}    name   Nombre de la Departamento que se va a mostrar al usuario.
 * @apiParam    {String}    [description]  La descripción del Departamento que se va a mostrar al usuario.
 * @apiParam    {String}    [photo]  En esta columna se almacena el path de ubicación de la imagen a mostrar del departamento.
 *
 * @apiUse      RespuestaSuccessInsertarEditar
 * @apiUse      ErrorAlEditar
 */

/**
 * @api         {delete}        /department/:id   Borrar
 * @apiVersion 1.0.0
 * @apiGroup    Departamento
 *
 * @apiExample {delete} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/department/135e656e6cda8640820350816ab59f2d
 *
 * @apiDescription  Permite borrar un Departamento. Solo es posible suprimir el Departamento, si éste no ha sido vinculado con un <code>Producto</code>.
 *                  Para eliminarlo, debe indicar el <code>id</code> del Departamento a borrar. La solicitud devuelve un objeto con un
 *                  parámetro <code>success=true</code> en caso de éxito. De lo contrario, esta llamada devuelve un error ( <code>success=false</code> )
 . Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}        id              El <code>id</code> del Departamento que se va a borrar.
 *
 * @apiName     BorraDepartamento
 *
 * @apiUse      SuccessAlBorrar
 *
 * @apiUse      ErrorAlBorrar
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
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/product/
 *
 * @apiDescription  Devuelve una lista de todos los Productos. Las Productos se devuelven ordenados por fecha de creación,
 *                  los Productos más recientemente creados aparecen en primer lugar. Las columnas disponibles a mostrar son
 *                  <code>id, department_id, name, description, reference, price, photo</code>
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
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/product/
 *
 * @apiDescription  Crea un nuevo objeto Producto. Las columnas guardar son <code>department_id, name, description, reference, price, photo</code>.
 *                   La solicitud retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito,
 *                   de lo contrario devuelve <code>status 400</code>. Para mas informacion vease los ejemplos a continuación.
 *
 * @apiParam    {String}    department_id   el valor <code>id</code> del Departamento al cual pertenece el Producto.
 * @apiParam    {String}    name            Nombre del Producto que se va a mostrar al usuario.
 * @apiParam    {String}    [description]   La descripción del Producto que se va a mostrar al usuario.
 * @apiParam    {String}    [reference]     Referencia del Producto.
 * @apiParam    {Decimal}   [price]         Precio de venta del Producto para mostrar. Ej. $57.99
 * @apiParam    {String}    [photo]         En esta columna se almacena el path de ubicación de la imagen a mostrar del producto.
 *
 * @apiUse  RespuestaSuccessInsertarEditar
 */

/**
 * @api      {get}          /product/:id   Leer
 * @apiDescription  Recupera los detalles de un Producto existente. Suministrar el <code>id</code> del Producto para devolver la
 *                  información de la Producto correspondiente. La respuesta devuelve un conjunto de variables, la variable
 *                  <code>result</code> contiene JSON con las siguientes columnas: <code>id, department_id, name, description, reference, price, photo</code>.
 * @apiVersion 1.0.0
 * @apiParam {Number}       id              El <code>id</code> del Producto que se está leyendo.
 * @apiName  GetProducto
 * @apiGroup Producto
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/product/135e656e6cda8640820350816ab59f2d
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
 *                      https://apirestfm.herokuapp.com/api/product/135e656e6cda8640820350816ab59f2d
 *
 * @apiDescription  Actualiza los detalles del Producto específico, mediante el identificación de los valores de los parámetros pasados.
 *                  Cualquiera de los parámetros no previstos serán dejados sin cambios. Las columnas a actualizar son
 *                  <code>department_id, name, description, reference, price, photo</code>.
 *                  Tenga en cuenta que el atributo <code>id</code> no es editable. La solicitud
 *                  retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito, de lo contrario devuelve <code>status 400</code>.
 *                  Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}    department_id   el valor <code>id</code> del Departamento al cual pertenece el Producto.
 * @apiParam    {String}    name            Nombre del Producto que se va a mostrar al usuario.
 * @apiParam    {String}    [description]   La descripción del Producto que se va a mostrar al usuario.
 * @apiParam    {String}    [reference]     Referencia del Producto.
 * @apiParam    {Decimal}   [price]         Precio de venta del Producto para mostrar. Ej. $57.99
 * @apiParam    {String}    [photo]         En esta columna se almacena el path de ubicación de la imagen a mostrar del producto.
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
 *                      https://apirestfm.herokuapp.com/api/product/135e656e6cda8640820350816ab59f2d
 *
 * @apiDescription  Permite borrar un Producto. Solo es posible suprimir el Producto, si éste no ha sido vinculado con una <code>Orden</code>.
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
 *                      https://apirestfm.herokuapp.com/api/customer/
 *
 * @apiDescription  Devuelve una lista de todos los Clientes. Los Clientes se devuelven ordenados por fecha de creación,
 *                  los Clientes más recientemente creados aparecen en primer lugar. Las columnas disponibles a mostrar son
     *                  <code>id, first_name, last_name, email, photo, phone, security_question, answer</code>
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
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/customer/
 *
 * @apiDescription  Crea un nuevo objeto Cliente. Las columnas guardar son <code>first_name, last_name, email, password, photo, phone, security_question, answer</code>.
 *                   La solicitud retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito,
 *                   de lo contrario devuelve <code>status 400</code>. Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}    first_name   Nombre completo del  Cliente.
 * @apiParam    {String}    last_name            Apellidos del Cliente.
 * @apiParam    {String}    email   Direccion de correo electronico, ademas se usa como login de acceso.
 * @apiParam    {String}    password     Clave de acceso a la aplicacion.
 * @apiParam    {String}    [photo]         En esta columna se almacena el path de ubicación de la imagen de perfil del cliente.
 * @apiParam    {String}    phone         Numero telefonico de contacto al cliente.
 * @apiParam    {String}    [security_question]     Pregunta de seguridad para recuperar clave de usuario.
 * @apiParam    {String}    [answer]         Respuesta a la pregunta de seguridad para recuperar clave de usuario.
 *
 * @apiUse  RespuestaSuccessInsertarEditar
 */

/**
 * @api      {get}          /customer/:id   Leer
 * @apiDescription  Recupera los detalles de un Cliente existente. Suministrar el <code>id</code> del Cliente para devolver la
 *                  información del Cliente correspondiente. La respuesta devuelve un conjunto de variables, la variable
 *                  <code>result</code> contiene JSON con las siguientes columnas: <br>
 *                      <code>id, first_name, last_name, email, password, photo, phone, security_question, answer</code>. <br>Tambien
 *                      devuelve un array de las tarjetas (<code>cards</code>) y direcciones de entrega (<code>address</code>)
 *                      asociadas al cliente.
 * @apiVersion 1.0.0
 * @apiParam {Number}       id              El <code>id</code> del Cliente que se está leyendo.
 * @apiName  GetCliente
 * @apiGroup Cliente
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/customer/135e656e6cda8640820350816ab59f2d
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
 *                      https://apirestfm.herokuapp.com/api/customer/135e656e6cda8640820350816ab59f2d
 *
 * @apiDescription  Actualiza los detalles del Cliente específico, mediante la identificación de los valores de los parámetros suministrados.
 *                  Cualquiera de los parámetros no previstos serán dejados sin cambios. Las columnas a actualizar son<br>
 *                  <code>first_name, last_name, email, password, photo, phone, security_question, answer</code>.<br>
 *                  Tenga en cuenta que el atributo <code>id</code> no es editable. La solicitud
 *                  retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito, de lo contrario devuelve <code>status 400</code>.
 *                  Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}    first_name   Nombre completo del  Cliente.
 * @apiParam    {String}    last_name            Apellidos del Cliente.
 * @apiParam    {String}    email   Direccion de correo electronico, ademas se usa como login de acceso.
 * @apiParam    {String}    password     Clave de acceso a la aplicacion.
 * @apiParam    {String}    [photo]         En esta columna se almacena el path de ubicación de la imagen de perfil del cliente.
 * @apiParam    {String}    phone         Numero telefonico de contacto al cliente.
 * @apiParam    {String}    [security_question]     Pregunta de seguridad para recuperar clave de usuario.
 * @apiParam    {String}    [answer]         Respuesta a la pregunta de seguridad para recuperar clave de usuario.
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
 *                      https://apirestfm.herokuapp.com/api/product/135e656e6cda8640820350816ab59f2d
 *
 * @apiDescription  Permite borrar un Cliente. Solo es posible suprimir el Cliente, si éste no ha sido vinculado con una <code>Orden</code>.
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

/**
 * @api      {post}         /customer/login/      Login
 * @apiVersion 1.0.0
 * @apiName  LoginCliente
 * @apiGroup Cliente
 *
 * @apiExample {post} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/customer/login
 *
 * @apiDescription  Valida los datos suministrados para el inicio de sesion (<code>email</code> y <code>password</code>).
 *                  La solicitud retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito,
 *                   de lo contrario devuelve <code>status 400</code>. Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}    email       Direccion de correo electronico del cliente usada como usuario de acceso a la app.
 * @apiParam    {String}    password    Clave de acceso del cliente usada para iniciar sesion en la app.
 *
 * @apiUse  RespuestaSuccessLogin
 *
 * @apiUse  RespuestaFallidaLogin
 */






// ------------------------------------------------------------------------------------------
// D E L I V E R Y M A N ...... repartidores.
// ------------------------------------------------------------------------------------------

/**
 * @api      {get}          /deliveryman/      Listar
 * @apiVersion 1.0.0
 * @apiName  GetAll
 * @apiGroup Repartidor
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/deliveryman/
 *
 * @apiDescription  Devuelve una lista de todos los Repartidores. Los Repartidores se devuelven ordenados por fecha de creación,
 *                  los Repartidores más recientemente creados aparecen en primer lugar. Las columnas disponibles a mostrar son
     *                  <code>id, first_name, last_name, email, photo, phone, security_question, answer</code>
 * @apiName  GetAll
 * @apiGroup Repartidor
 *
 * @apiUse  RespuestaSuccessListar
 */

/**
 * @api      {post}         /deliveryman/      Crear
 * @apiVersion 1.0.0
 * @apiName  CreateRepartidor
 * @apiGroup Repartidor
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/deliveryman/
 *
 * @apiDescription  Crea un nuevo objeto Repartidor. Las columnas guardar son <code>first_name, last_name, email, password, photo, phone, security_question, answer</code>.
 *                   La solicitud retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito,
 *                   de lo contrario devuelve <code>status 400</code>. Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}    first_name  Nombres del  Repartidor.
 * @apiParam    {String}    last_name   Apellidos del Repartidor.
 * @apiParam    {String}    email       Direccion de correo electronico, ésta columna es obligatoria para iniciar sesión.
 * @apiParam    {String}    password    Clave de acceso, ésta columna es obligatoria para iniciar sesión.
 * @apiParam    {String}    [photo]     En esta columna se almacena el path de ubicación de la imagen de perfil del repartidor.
 * @apiParam    {String}    phone       Numero telefonico de contacto al cliente.
 * @apiParam    {String}    [security_question] Pregunta de seguridad para recuperar clave de usuario.
 * @apiParam    {String}    [answer]    Respuesta a la pregunta de seguridad para recuperar clave de usuario.
 *
 * @apiUse  RespuestaSuccessInsertarEditar
 */

/**
 * @api      {get}          /deliveryman/:id   Leer
 * @apiDescription  Recupera los detalles de un Repartidor existente. Suministrar el <code>id</code> del Repartidor para devolver la
 *                  información del Repartidor correspondiente. La respuesta devuelve un conjunto de variables, la variable
 *                  <code>result</code> contiene JSON con las siguientes columnas: <br>
 *                      <code>id, first_name, last_name, email, password, photo, phone, security_question, answer</code>. <br>Tambien
 *                      devuelve un array de las tarjetas (<code>cards</code>) y direcciones de entrega (<code>address</code>)
 *                      asociadas al cliente.
 * @apiVersion 1.0.0
 * @apiParam {Number}       id              El <code>id</code> del Repartidor que se está leyendo.
 * @apiName  GetRepartidor
 * @apiGroup Repartidor
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/deliveryman/135e656e6cda8640820350816ab59f2d
 *
 * @apiUse  RespuestaSuccessListar
 */

/**
 * @api         {put}           /deliveryman/:id   Editar
 * @apiVersion 1.0.0
 * @apiName     EditRepartidor
 * @apiGroup    Repartidor
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/deliveryman/135e656e6cda8640820350816ab59f2d
 *
 * @apiDescription  Actualiza los detalles del Repartidor específico, mediante la identificación de los valores de los parámetros suministrados.
 *                  Cualquiera de los parámetros no previstos serán dejados sin cambios. Las columnas a actualizar son<br>
 *                  <code>first_name, last_name, email, password, photo, phone, security_question, answer</code>.<br>
 *                  Tenga en cuenta que el atributo <code>id</code> no es editable. La solicitud
 *                  retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito, de lo contrario devuelve <code>status 400</code>.
 *                  Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}    first_name  Nombres del  Repartidor.
 * @apiParam    {String}    last_name   Apellidos del Repartidor.
 * @apiParam    {String}    email       Direccion de correo electronico, ésta columna es obligatoria para iniciar sesión.
 * @apiParam    {String}    password    Clave de acceso, ésta columna es obligatoria para iniciar sesión.
 * @apiParam    {String}    [photo]     En esta columna se almacena el path de ubicación de la imagen de perfil del repartidor.
 * @apiParam    {String}    phone       Numero telefonico de contacto al cliente.
 * @apiParam    {String}    [security_question] Pregunta de seguridad para recuperar clave de usuario.
 * @apiParam    {String}    [answer]    Respuesta a la pregunta de seguridad para recuperar clave de usuario.
 *
 * @apiUse      RespuestaSuccessInsertarEditar
 * @apiUse      ErrorAlEditar
 */

/**
 * @api         {delete}        /deliveryman/:id   Borrar
 * @apiVersion 1.0.0
 * @apiGroup    Repartidor
 *
 * @apiExample {delete} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/product/135e656e6cda8640820350816ab59f2d
 *
 * @apiDescription  Permite borrar un Repartidor. Solo es posible suprimir el Repartidor, si éste no ha sido vinculado con una <code>Orden</code>.
 *                  Para eliminarlo, debe indicar el <code>id</code> del Repartidor a borrar. La solicitud devuelve un objeto con un
 *                  parámetro <code>success=true</code> en caso de éxito. De lo contrario, esta llamada devuelve un error ( <code>success=false</code> )
 . Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}        id              El <code>id</code> del Repartidor que se va a borrar.
 *
 * @apiName     BorraRepartidor
 *
 * @apiUse      SuccessAlBorrar
 *
 * @apiUse      ErrorAlBorrar
 *
 */


/**
 * @api      {post}         /deliveryman/login/      Login
 * @apiVersion 1.0.0
 * @apiName  LoginRepartidor
 * @apiGroup Repartidor
 *
 * @apiExample {post} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/deliveryman/login
 *
 * @apiDescription  Valida los datos suministrados para el inicio de sesion (<code>email</code> y <code>password</code>).
 *                  La solicitud retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito,
 *                   de lo contrario devuelve <code>status 400</code>. Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}    email       Direccion de correo electronico del repartidor usada como usuario de acceso a la app.
 * @apiParam    {String}    password    Clave de acceso del repartidor usada para iniciar sesion en la app.
 *
 * @apiUse  RespuestaSuccessLogin
 *
 * @apiUse  RespuestaFallidaLogin
 */









// ------------------------------------------------------------------------------------------
// C A R D S.
// ------------------------------------------------------------------------------------------

/**
 * @api      {get}          /card/      Listar
 * @apiVersion 1.0.0
 * @apiName  GetAll
 * @apiGroup Tarjeta
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/department/
 *
 * @apiDescription  Devuelve una lista de todas las Tarjetas. Las Tarjetas se devuelven ordenadas por fecha de creación,
 *                  las Tarjetas más recientemente creados aparecen en primer lugar. Las columnas disponibles a mostrar son
 *                  <code>id, customer_id, card_number, name_on_card, exp_date, ccv, type</code>
 * @apiName  GetAll
 * @apiGroup Tarjeta
 *
 * @apiUse  RespuestaSuccessListar
 */

/**
 * @api      {post}         /card/      Crear
 * @apiVersion 1.0.0
 * @apiName  CreateTarjeta
 * @apiGroup Tarjeta
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/card/
 *
 * @apiDescription  Crea un nuevo objeto Tarjeta. Las columnas guardar son <code>customer_id, card_number, name_on_card, exp_date, ccv, type</code>.
 *                   La solicitud retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito,
 *                   de lo contrario devuelve <code>status 400</code>. Para mas informacion vease los ejemplos a continuación.
 *
 * @apiParam    {String}    customer_id el valor <code>id</code> del Cliente al cual pertenece la Tarjeta. <br>
 *                                      Ej. <code>customer_id</code>=135e656e6cda8640820350816ab59f2d.
 * @apiParam    {String}    card_number Numero que aparece en la parte frontal de la Tarjeta. Compuesto por 16 numeros en grupos de 4 numeros, separados
 *                          por guion. Ej 1234-5678-9012-3456.
 * @apiParam    {String}    name_on_card    Nombre impreso en la tarjeta. Ej. JOHN A DOE.
 * @apiParam    {String}    exp_date        Fecha de vencimiento de la tarjeta. Ej. <code>05-19</code>  o  <code>05/19</code>.
 * @apiParam    {String}    ccv             Numeros del ultimo bloque de 3 digitos impresos por el reverso de la tarjeta. Ej. 1234-5678-9012-3456
 *                                          <span class='label label-alert'>789</span>.
 * @apiParam    {String}    [type]          Tipo de tarjeta: <code>VISA, MASTER, DINERS, AMERICAN EXPRESS   </code>
 *
 * @apiUse  RespuestaSuccessInsertarEditar
 */

/**
 * @api      {get}          /card/:id   Leer
 * @apiDescription  Recupera los detalles de una Tarjeta existente. Suministrar el <code>id</code> de la Tarjeta para devolver la
 *                  información de correspondiente. La respuesta devuelve un conjunto de variables, la variable
 *                  <code>result</code> contiene JSON con las siguientes columnas: <code>id, customer_id, card_number, name_on_card, exp_date, ccv, type</code>
 *                  y una columna con la información del Cliente (<code>customer</code>).
 * @apiVersion 1.0.0
 * @apiParam {Number}       id              El <code>id</code> de la Tarjeta que se está leyendo.
 * @apiName  GetTarjeta
 * @apiGroup Tarjeta
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/card/135e656e6cda8640820350816ab59f2d
 *
 * @apiUse  RespuestaSuccessListar
 */

/**
 * @api         {put}           /card/:id   Editar
 * @apiVersion 1.0.0
 * @apiName     EditTarjeta
 * @apiGroup    Tarjeta
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/card/135e656e6cda8640820350816ab59f2d
 *
 * @apiDescription  Actualiza los detalles de la Tarjeta específica, mediante el identificación de los valores de los parámetros pasados.
 *                  Cualquiera de los parámetros no previstos serán dejados sin cambios. Las columnas a actualizar son
 *                  <code>id, customer_id, card_number, name_on_card, exp_date, ccv, type</code>.
 *                  Tenga en cuenta que el atributo <code>id</code> no es editable. La solicitud
 *                  retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito, de lo contrario devuelve <code>status 400</code>.
 *                  Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}    customer_id el valor <code>id</code> del Cliente al cual pertenece la Tarjeta.<br>
 *                                      Ej. <code>customer_id</code>=135e656e6cda8640820350816ab59f2d.
 * @apiParam    {String}    card_number Numero que aparece en la parte frontal de la Tarjeta. Compuesto por 16 numeros en grupos de 4 numeros, separados
 *                          por guion. Ej 1234-5678-9012-3456.
 * @apiParam    {String}    name_on_card    Nombre impreso en la tarjeta. Ej. JOHN A DOE.
 * @apiParam    {String}    exp_date        Fecha de vencimiento de la tarjeta. Ej. <code>05-19</code>  o  <code>05/19</code>.
 * @apiParam    {String}    ccv             Numeros del ultimo bloque de 3 digitos impresos por el reverso de la tarjeta. Ej. 1234-5678-9012-3456
 *                                          <span class='label label-alert'>789</span>.
 * @apiParam    {String}    [type]          Tipo de tarjeta: <code>VISA, MASTER, DINERS, AMERICAN EXPRESS   </code>
 *
 * @apiUse      RespuestaSuccessInsertarEditar
 * @apiUse      ErrorAlEditar
 */

/**
 * @api         {delete}        /card/:id   Borrar
 * @apiVersion 1.0.0
 * @apiGroup    Tarjeta
 *
 * @apiExample {delete} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/card/135e656e6cda8640820350816ab59f2d
 *
 * @apiDescription  Permite borrar una Tarjeta. Solo es posible suprimir la Tarjeta, si ésta no ha sido vinculada con una <code>Orden</code>.
 *                  Para eliminarla, debe indicar el <code>id</code> de la Tarjeta a borrar. La solicitud devuelve un objeto con un
 *                  parámetro <code>success=true</code> en caso de éxito. De lo contrario, esta llamada devuelve un error ( <code>success=false</code> )
 . Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}        id              El <code>id</code> de la Tarjeta que se va a borrar.
 *
 * @apiName     BorraTarjeta
 *
 * @apiUse      SuccessAlBorrar
 *
 * @apiUse      ErrorAlBorrar
 */








// ------------------------------------------------------------------------------------------
// A D D R E S S.
// ------------------------------------------------------------------------------------------

/**
 * @api      {get}          /address/      Listar
 * @apiVersion 1.0.0
 * @apiName  GetAll
 * @apiGroup Direccion
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/department/
 *
 * @apiDescription  Devuelve una lista de todas las Direcciones. Las Direcciones se devuelven ordenadas por fecha de creación,
 *                  las Direcciones más recientemente creados aparecen en primer lugar. Las columnas disponibles a mostrar son
 *                  <code>id, customer_id, address, address2, lat, lng</code>
 * @apiName  GetAll
 * @apiGroup Direccion
 *
 * @apiUse  RespuestaSuccessListar
 */

/**
 * @api      {post}         /address/      Crear
 * @apiVersion 1.0.0
 * @apiName  CreateDireccion
 * @apiGroup Direccion
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/address/
 *
 * @apiDescription  Crea un nuevo objeto Direccion de entrega de Pedidos. Las columnas guardar son <code>customer_id, address, address2, lat, lng</code>.
 *                   La solicitud retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito,
 *                   de lo contrario devuelve <code>status 400</code>. Para mas informacion vease los ejemplos a continuación.
 *
 * @apiParam    {String}    customer_id el valor <code>id</code> del Cliente al cual pertenece la Dirección de entrega. <br>
 *                                          Ej. <code>customer_id</code>=135e656e6cda8640820350816ab59f2d.
 * @apiParam    {String}    address     Descripcion de la dirección de entrega. Ej. Brisas a282,Corredor Nte.,Panama City
 * @apiParam    {String}    [address2]  Continuacion de la descripcion de la dirección de entrega.(Opcional)
 * @apiParam    {Float}     [lat]       coordenada latitud de la direccion de entrega del pedido, expresada en medida angular que varía entre los 0° del ecuador
 *                                          hasta los 90°. Ej. 8.97737
 * @apiParam    {Float}     [lng]       coordenada longitud de la direccion de entrega del pedido, expresada en medida angular que varía entre los 0° del ecuador
 *                                          hasta los 180°. Ej. -79.51831
 *
 * @apiUse  RespuestaSuccessInsertarEditar
 */

/**
 * @api      {get}          /address/:id   Leer
 * @apiDescription  Recupera los detalles de una Direccion de entrega de pedidos existente. Suministrar el <code>id</code> de la Direccion para devolver la
 *                  información de correspondiente. La respuesta devuelve un conjunto de variables, la variable
 *                  <code>result</code> contiene JSON con las siguientes columnas: <code>id, customer_id, address, address2, lat, lng</code>
 *                  y una columna con la información del Cliente asociado a esta direccion (<code>customer</code>).
 * @apiVersion 1.0.0
 * @apiParam {Number}       id              El <code>id</code> de la Direccion que se está leyendo.
 * @apiName  GetDireccion
 * @apiGroup Direccion
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/address/135e656e6cda8640820350816ab59f2d
 *
 * @apiUse  RespuestaSuccessListar
 */

/**
 * @api         {put}           /address/:id   Editar
 * @apiVersion 1.0.0
 * @apiName     EditDireccion
 * @apiGroup    Direccion
 *
 * @apiExample {put} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/address/135e656e6cda8640820350816ab59f2d
 *
 * @apiDescription  Actualiza los detalles de la Direccion de entrega específica, mediante el identificación de los valores de los parámetros pasados.
 *                  Cualquiera de los parámetros no previstos serán dejados sin cambios. Las columnas a actualizar son
 *                  <code>customer_id, address, address2, lat, lng</code>. Tenga en cuenta que el atributo <code>id</code> no es editable. La solicitud
 *                  retorna un JSON con la informacion con <code>status 200</code> en caso de tener exito, de lo contrario devuelve <code>status 400</code>.
 *                  Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}    customer_id el valor <code>id</code> del Cliente al cual pertenece la Dirección de entrega. <br>
 *                                          Ej. <code>customer_id</code>=135e656e6cda8640820350816ab59f2d.
 * @apiParam    {String}    address     Descripcion de la dirección de entrega. Ej. Brisas a282,Corredor Nte.,Panama City
 * @apiParam    {String}    [address2]  Continuacion de la descripcion de la dirección de entrega.(Opcional)
 * @apiParam    {Float}     [lat]       coordenada latitud de la direccion de entrega del pedido, expresada en medida angular que varía entre los 0° del ecuador
 *                                          hasta los 90°. Ej. 8.97737
 * @apiParam    {Float}     [lng]       coordenada longitud de la direccion de entrega del pedido, expresada en medida angular que varía entre los 0° del ecuador
 *                                          hasta los 180°. Ej. -79.51831
 *
 * @apiUse      RespuestaSuccessInsertarEditar
 * @apiUse      ErrorAlEditar
 */

/**
 * @api         {delete}        /address/:id   Borrar
 * @apiVersion 1.0.0
 * @apiGroup    Direccion
 *
 * @apiExample {delete} Ejemplo de Solicitud:
 *                      https://apirestfm.herokuapp.com/api/address/135e656e6cda8640820350816ab59f2d
 *
 * @apiDescription  Permite borrar una Dirección de entrega. Solo es posible suprimir la Dirección de entrega, si ésta aun no ha sido asociada a una <code>Orden</code>.
 *                  Para eliminarla, debe indicar el <code>id</code> de la Dirección a borrar. La solicitud devuelve un objeto con un
 *                  parámetro <code>success=true</code> en caso de éxito. De lo contrario, esta llamada devuelve un error ( <code>success=false</code> )
 . Para mas información vease los ejemplos a continuación.
 *
 * @apiParam    {String}        id              El <code>id</code> de la Dirección de entrega que se va a borrar.
 *
 * @apiName     BorraDireccion
 *
 * @apiUse      SuccessAlBorrar
 *
 * @apiUse      ErrorAlBorrar
 */



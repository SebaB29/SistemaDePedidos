import { helpHttp } from '../helpers/helpHttp'
import { useFrom } from '../hooks/useForm'
import Loader from './Loader'
import Message from './Message'

const initialForm = {
  username: '',
  lastName: '',
  email: '',
  age: '',
  password: '',
  photo: '',
  gender: '',
  address: ''
}

const validationsForm = (form) => {
  const errors = {}

  const regex = {
    username: /^[A-Za-zÑñÁáÉéÍíÓóÚúÜü\s]+$/,
    lastName: /^[A-Za-zÑñÁáÉéÍíÓóÚúÜü\s]+$/,
    email: /^(\w+[/./-]?){1,}@[a-z]+[/.]\w{2,}$/,
    password: /.*/,
    age: /^(1[89]|[2-9][0-9]|1[01][0-9]|120)$/,
    photo: /^(https?:\/\/.*\.(?:jpg|jpeg|png))$/i,
    gender: /.*/,
    address: /.*/
  }

  const message = {
    username: "El campo 'Nombre' solo acepta mayusculas, minusculas y espacios en blanco",
    lastName: "El campo 'Apellido' solo acepta mayusculas, minusculas y espacios en blanco",
    email: "El campo 'Email' es incorrecto",
    password: '',
    age: 'Debes ser mayor de 18 y menor de 120',
    photo: 'El campo foto solo acepta urls de imagenes jpg, jpeg y png',
    gender: '',
    address: ''
  }

  Object.keys(form).forEach(el => {
    if (!form[el].trim()) {
      errors[el] = 'Este campo es requerido'
    } else if (!regex[el].test(form[el].trim())) {
      errors[el] = message[el]
    }
  })

  return errors
}

const styles = {
  color: '#dc3545',
  fontWeight: 'bold'
}

const RegisterForm = () => {
  const ENDPOINT = 'http://localhost:8080/auth/register'
  const {
    form,
    errors,
    loading,
    response,
    handleChange,
    handleBlur,
    handleSubmit
  } = useFrom(initialForm, validationsForm, helpHttp().post, ENDPOINT)

  return (
    <div>
      <h2>Formulario de Registro</h2>
      <form onSubmit={handleSubmit}>
        <input
          type='text'
          name='username'
          placeholder='Escribe tu nombre'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.username}
          required
        />
        {errors.username && <p style={styles}>{errors.username}</p>}
        <input
          type='text'
          name='lastName'
          placeholder='Escribe tu Apellido'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.lastName}
          required
        />
        {errors.lastName && <p style={styles}>{errors.lastName}</p>}
        <input
          type='email'
          name='email'
          placeholder='Escribe tu email'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.email}
          required
        />
        {errors.email && <p style={styles}>{errors.email}</p>}
        <input
          type='number'
          placeholder='Edad'
          min='18'
          max='120'
          name='age'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.age}
          required
        />
        {errors.age && <p style={styles}>{errors.age}</p>}
        <input
          type='password'
          name='password'
          placeholder='Escribe tu Contraseña'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.password}
          required
        />
        {errors.password && <p style={styles}>{errors.password}</p>}
        <input
          type='url'
          name='photo'
          placeholder='Ingrese url de su foto'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.photo}
          required
        />
        {errors.photo && <p style={styles}>{errors.photo}</p>}
        <input
          type='text'
          name='gender'
          placeholder='Escribe tu genero'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.gender}
          required
        />
        {errors.gender && <p style={styles}>{errors.gender}</p>}
        <input
          type='text'
          name='address'
          placeholder='Escribe tu direccion'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.address}
          required
        />
        {errors.address && <p style={styles}>{errors.address}</p>}
        <input type='submit' value='Registrarme' />
      </form>
      {loading && <Loader />}
      {response && response.status === 'OK'
        ? <Message bgColor='#198754' message={response.status + ': ' + response.data.message} />
        : response && <Message bgColor='#cb0505' message={response.error} />}
    </div>
  )
}

export default RegisterForm

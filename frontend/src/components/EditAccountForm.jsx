import { helpHttp } from '../helpers/helpHttp'
import { useFrom } from '../hooks/useForm'
import Loader from './Loader'
import Message from './Message'

const initialForm = {
  name: window.sessionStorage.getItem('name'),
  last_name: window.sessionStorage.getItem('last_name'),
  age: window.sessionStorage.getItem('age'),
  password: window.sessionStorage.getItem('password'),
  photo: window.sessionStorage.getItem('photo'),
  gender: window.sessionStorage.getItem('gender'),
  address: window.sessionStorage.getItem('address')
}

const validationsForm = (form) => {
  const errors = {}

  const regex = {
    name: /^[A-Za-zÑñÁáÉéÍíÓóÚúÜü\s]+$/,
    last_name: /^[A-Za-zÑñÁáÉéÍíÓóÚúÜü\s]+$/,
    password: /.*/,
    age: /^(1[89]|[2-9][0-9]|1[01][0-9]|120)$/,
    photo: /^(https?:\/\/.*\.(?:jpg|jpeg|png))$/i,
    gender: /.*/,
    address: /.*/
  }

  const message = {
    name: "El campo 'Nombre' solo acepta mayusculas, minusculas y espacios en blanco",
    last_name: "El campo 'Apellido' solo acepta mayusculas, minusculas y espacios en blanco",
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

export const EditAccountForm = () => {
  const ENDPOINT = ''
  const {
    form,
    errors,
    loading,
    response,
    handleChange,
    handleBlur,
    handleSubmit
  } = useFrom(initialForm, validationsForm, helpHttp().put, ENDPOINT)

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <input
          type='text'
          name='name'
          placeholder='Escribe tu nombre'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.name}
          required
        />
        {errors.name && <p style={styles}>{errors.name}</p>}
        <input
          type='text'
          name='last_name'
          placeholder='Escribe tu Apellido'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.last_name}
          required
        />
        {errors.last_name && <p style={styles}>{errors.last_name}</p>}
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
        <input type='submit' value='Aceptar' />
      </form>
      {loading && <Loader />}
      {response && <Message bgColor='#198754' message='Se obtuvo una respuesta del server' />}
    </div>
  )
}

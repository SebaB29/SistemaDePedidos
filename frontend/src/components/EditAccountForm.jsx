import { useEffect } from 'react'
import { helpHttp } from '../helpers/helpHttp'
import { useFrom } from '../hooks/useForm'
import Loader from './Loader'

const validationsForm = (form) => {
  const errors = {}

  const regex = {
    userName: /^[A-Za-zÑñÁáÉéÍíÓóÚúÜü\s]+$/,
    lastName: /^[A-Za-zÑñÁáÉéÍíÓóÚúÜü\s]+$/,
    email: /.*/,
    password: /.*/,
    age: /^(1[89]|[2-9][0-9]|1[01][0-9]|120)$/,
    photo: /^(https?:\/\/.*\.(?:jpg|jpeg|png))$/i,
    gender: /.*/,
    address: /.*/
  }

  const message = {
    userName: "El campo 'Nombre' solo acepta mayusculas, minusculas y espacios en blanco",
    lastName: "El campo 'Apellido' solo acepta mayusculas, minusculas y espacios en blanco",
    password: '',
    age: 'Debes ser mayor de 18 y menor de 120',
    photo: 'El campo foto solo acepta urls de imagenes jpg, jpeg y png',
    gender: '',
    address: ''
  }

  Object.keys(form).forEach(el => {
    if (el === 'password') return
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

export const EditAccountForm = ({ data }) => {
  const ENDPOINT = 'http://localhost:8080/user'
  const initialForm = {
    userName: data.username,
    lastName: data.lastName,
    email: data.email,
    age: data.age.toString(),
    password: '',
    photo: data.photo,
    gender: data.gender,
    address: data.address
  }
  const {
    form,
    errors,
    loading,
    response,
    handleChange,
    handleBlur,
    handleSubmit
  } = useFrom(initialForm, validationsForm, helpHttp().put, ENDPOINT)

  useEffect(() => {
    if (response === null) return
    console.log(response)
    if (response.status === 'OK') {
      window.alert(response.data.message)
      window.location.reload()
    } else {
      window.alert(response.error)
    }
  }, [response])

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <input
          type='text'
          name='userName'
          placeholder='Escribe tu nombre'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.userName}
          required
        />
        {errors.userName && <p style={styles}>{errors.userName}</p>}
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
        <input
          type='hidden'
          name='password'
          placeholder='Escribe tu Contraseña'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.password}
          required
        />
        {errors.password && <p style={styles}>{errors.password}</p>}
        <input type='submit' value='Aceptar' />
      </form>
      {loading && <Loader />}
    </div>
  )
}

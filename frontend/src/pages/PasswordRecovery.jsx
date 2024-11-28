import { useEffect } from 'react'
import Loader from '../components/Loader'
import { helpHttp } from '../helpers/helpHttp'
import { useFrom } from '../hooks/useForm'

const ENDPOINT = 'http://localhost:8080/auth/restore'

const initialForm = {
  email: '',
  newPassword: ''
}

const validationsForm = (form) => {
  const errors = {}

  const regex = {
    email: /^(\w+[/./-]?){1,}@[a-z]+[/.]\w{2,}$/,
    newPassword: /.*/
  }

  const message = {
    email: "El campo 'Email' es incorrecto",
    newPassword: ''
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

const PasswordRecovery = () => {
  const {
    form, errors, loading, response, handleChange, handleBlur, handleSubmit
  } = useFrom(initialForm, validationsForm, helpHttp().put, ENDPOINT)

  useEffect(() => {
    if (response === null) return
    if (response.status === 'BAD_REQUEST') {
      window.alert(response.error)
    } else if (response.status === 'OK') {
      window.alert('Contraseña Restaurada')
    }
  }, [response])

  return (
    <div>
      <h2>Restaurar contraseña</h2>
      <form onSubmit={handleSubmit}>
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
          type='password'
          name='newPassword'
          placeholder='Escribe tu Nueva Contraseña'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.newPassword}
          required
        />
        {errors.newPassword && <p style={styles}>{errors.newPassword}</p>}
        <input type='submit' value='Restaurar' />
      </form>
      {loading && <Loader />}
    </div>
  )
}

export default PasswordRecovery

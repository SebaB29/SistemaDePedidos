import { useNavigate } from 'react-router-dom'
import { helpHttp } from '../helpers/helpHttp'
import { useFrom } from '../hooks/useForm'
import Loader from './Loader'
import Message from './Message'
import { useEffect, useState } from 'react'

const ENDPOINT = 'http://localhost:8080/auth/login'

const initialForm = {
  email: '',
  password: ''
}

const validationsForm = (form) => {
  const errors = {}

  const regex = {
    email: /^(\w+[/./-]?){1,}@[a-z]+[/.]\w{2,}$/,
    password: /.*/
  }

  const message = {
    email: "El campo 'Email' es incorrecto",
    password: ''
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

const LoginForm = () => {
  const {
    form, errors, loading, response, handleChange, handleBlur, handleSubmit
  } = useFrom(initialForm, validationsForm, helpHttp().post, ENDPOINT)
  const navigate = useNavigate()
  const [errorMessage, setErrorMessage] = useState('')

  useEffect(() => {
    if (response === null) return
    if (response.status === 'OK') {
      window.sessionStorage.setItem('access_token', response.data.access_token)
      window.sessionStorage.setItem('refresh_token', response.data.refresh_token)
      window.alert('Ingresado correctamente')
      navigate('/products')
    } else {
      setErrorMessage(response.status + ' : ' + response.error)
    }
  }, [response])

  return (
    <div>
      <h2>Login</h2>
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
          name='password'
          placeholder='Escribe tu Contraseña'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.password}
          required
        />
        {errors.password && <p style={styles}>{errors.password}</p>}
        <input type='submit' value='Ingresar' />
      </form>
      <button onClick={() => navigate('/register')}>Registrarse</button>
      <button onClick={() => navigate('/forgot_password')}>Olvidé mi contraseña</button>
      {loading && <Loader />}
      {errorMessage && <Message bgColor='#ff0000' message={errorMessage} />}
    </div>
  )
}

export default LoginForm

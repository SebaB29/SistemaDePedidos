import { useEffect } from 'react'
import Loader from '../components/Loader'
import { helpHttp } from '../helpers/helpHttp'
import { useFrom } from '../hooks/useForm'

const ENDPOINT = 'http://localhost:8080/product'

const styles = {
  color: '#dc3545',
  fontWeight: 'bold'
}

const validationsForm = (form) => {
  const errors = {}

  const regex = {
    product_name: /^[A-Za-zÑñÁáÉéÍíÓóÚúÜü\s]+$/
  }

  const message = {
    product_name: "El campo 'Nombre' solo acepta mayusculas, minusculas y espacios en blanco"
  }

  Object.keys(form).forEach(el => {
    if (el !== 'product_name') return
    if (!form[el].trim()) {
      errors[el] = 'Este campo es requerido'
    } else if (!regex[el].test(form[el].trim())) {
      errors[el] = message[el]
    }
  })

  return errors
}

export const EditProductForm = ({ product }) => {
  const initialForm = {
    product_name: product.name,
    product_id: product.productId,
    attributes: product.attributes
  }
  const {
    form, errors, loading, response,
    handleChange, handleBlur, handleSubmit, handleAddPropertie, handleChangeProp
  } = useFrom(initialForm, validationsForm, helpHttp().put, ENDPOINT)

  useEffect(() => {
    if (response === null) return
    if (response.status === 'OK') {
      window.alert(response.data.message)
      window.location.reload()
    } else {
      window.alert(response.error)
    }
  }, [response])

  return (
    <>
      <form onSubmit={handleSubmit}>
        <input
          type='text'
          name='product_name'
          placeholder='Nombre del producto'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.product_name}
          required
        />
        {errors.product_name && <p style={styles}>{errors.product_name}</p>}
        {form.attributes.map((attribute, index) => (
          <div key={index}>
            <hr />
            <input
              value={attribute.description}
              type='text'
              name='description'
              placeholder='Propiedad'
              required
              onChange={(e) => handleChangeProp(e, index)}
            />
            <input
              value={attribute.value}
              index={index}
              type='text'
              name='value'
              placeholder='Descripcion'
              onChange={(e) => handleChangeProp(e, index)}
              required
            />
          </div>
        ))}
        <hr />
        <button onClick={handleAddPropertie}>Agregar propiedad</button>
        <br /><br />
        <input type='submit' value='Editar' />
      </form>
      {loading && <Loader />}
    </>
  )
}

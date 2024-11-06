import Loader from '../components/Loader'
import { helpHttp } from '../helpers/helpHttp'
import { useFrom } from '../hooks/useForm'

const styles = {
  color: '#dc3545',
  fontWeight: 'bold'
}

const validationsForm = (form) => {
  const errors = {}

  const regex = {
    name: /^[A-Za-zÑñÁáÉéÍíÓóÚúÜü\s]+$/,
    stock: /^(0|[1-9][0-9]*)$/
  }

  const message = {
    name: "El campo 'Nombre' solo acepta mayusculas, minusculas y espacios en blanco",
    stock: 'Stock invalido, solo acepta numeros positivos y 0'
  }

  Object.keys(form).forEach(el => {
    if (el !== 'name' || el !== 'stock') return
    if (!form[el].trim()) {
      errors[el] = 'Este campo es requerido'
    } else if (!regex[el].test(form[el].trim())) {
      errors[el] = message[el]
    }
  })

  return errors
}

export const EditProductForm = ({ product }) => {
  const ENDPOINT = ''
  const {
    form, errors, loading,
    handleChange, handleBlur, handleSubmit, handleAddPropertie, handleChangeProp
  } = useFrom(product, validationsForm, helpHttp.put, ENDPOINT)

  return (
    <>
      <form onSubmit={handleSubmit}>
        <input
          type='text'
          name='name'
          placeholder='Nombre del producto'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.name}
          required
        />
        {errors.name && <p style={styles}>{errors.name}</p>}
        <input
          type='text'
          name='stock'
          placeholder='Cantidad'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.stock}
          required
        />
        {errors.stock && <p style={styles}>{errors.stock}</p>}
        {form.properties.map((propertie, index) => (
          <div key={index}>
            <hr />
            <input
              value={propertie.name}
              type='text'
              name='name'
              placeholder='Propiedad'
              required
              onChange={(e) => handleChangeProp(e, index)}
            />
            <input
              value={propertie.description}
              index={index}
              type='text'
              name='value'
              placeholder='Descripcion'
              onChange={(e) => handleChangeProp(e, index)}
              required
            />
          </div>
        ))}
        <button onClick={handleAddPropertie}>Agregar propiedad</button>
        <br /><br />
        <input type='submit' value='Editar' />
      </form>
      {loading && <Loader />}
      {/* {!response.ok && <Message bgColor='#ff0000' message={response.message} />} */}

    </>
  )
}

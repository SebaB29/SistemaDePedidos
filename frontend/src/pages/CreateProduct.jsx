import Header from '../components/Header'
import Loader from '../components/Loader'
import Message from '../components/Message'
import { helpHttp } from '../helpers/helpHttp'
import { useFrom } from '../hooks/useForm'

const ENDPOINT = 'http://localhost:8080/product'

const initialForm = {
  adminId: window.sessionStorage.getItem('user_id'),
  productName: '',
  stockType: '',
  quantity: '',
  attributes: []
}

const styles = {
  color: '#dc3545',
  fontWeight: 'bold'
}

const validationsForm = (form) => {
  const errors = {}

  const regex = {
    productName: /^[A-Za-zÑñÁáÉéÍíÓóÚúÜü\s]+$/,
    quantity: /^(0|[1-9][0-9]*)$/,
    stockType: /.*/
  }

  const message = {
    productName: "El campo 'Nombre' solo acepta mayusculas, minusculas y espacios en blanco",
    quantity: 'Stock invalido, solo acepta numeros positivos y 0',
    stockType: ''
  }

  Object.keys(form).forEach(el => {
    if (el === 'attributes' || el === 'adminId') return
    if (!form[el].trim()) {
      errors[el] = 'Este campo es requerido'
    } else if (!regex[el].test(form[el].trim())) {
      errors[el] = message[el]
    }
  })

  return errors
}

export const CreateProduct = () => {
  const {
    form, errors, loading, response,
    handleChange, handleBlur, handleSubmit, handleAddPropertie, handleChangeProp
  } = useFrom(initialForm, validationsForm, helpHttp().post, ENDPOINT)

  const handleSubmitCreateProduct = (e) => {
    const body = {
      adminId: Number(initialForm.adminId),
      product_name: form.productName,
      stock_type: form.stockType,
      quantity: parseFloat(form.quantity),
      attributes: form.attributes
    }
    handleSubmit(e, {}, body)
  }

  return (
    <>
      <Header title='Crear Producto' />
      <br />
      <form onSubmit={handleSubmitCreateProduct}>
        <input
          type='text'
          name='productName'
          placeholder='Nombre del producto'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.productName}
          required
        />
        {errors.productName && <p style={styles}>{errors.productName}</p>}
        <input
          type='text'
          name='stockType'
          placeholder='Tipo de stock'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.stockType}
          required
        />
        {errors.stockType && <p style={styles}>{errors.stockType}</p>}
        <input
          type='text'
          name='quantity'
          placeholder='Cantidad'
          onBlur={handleBlur}
          onChange={handleChange}
          value={form.quantity}
          required
        />
        {errors.quantity && <p style={styles}>{errors.quantity}</p>}
        {form.attributes.map((attribute, index) => (
          <div key={index}>
            <hr />
            <input
              type='text'
              name='description'
              placeholder='Propiedad'
              required
              onChange={(e) => handleChangeProp(e, index)}
            />
            <input
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
        <input type='submit' value='Crear' />
      </form>
      {loading && <Loader />}
      {(response !== null && response.status === 'CREATED') && <Message bgColor='#198754' message={response.data.message} />}

    </>
  )
}

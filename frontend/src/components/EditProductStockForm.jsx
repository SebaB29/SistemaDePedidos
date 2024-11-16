import React, { useEffect } from 'react'
import { helpHttp } from '../helpers/helpHttp'
import { useFrom } from '../hooks/useForm'
import Loader from './Loader'

const styles = {
  color: '#dc3545',
  fontWeight: 'bold'
}

const validationsForm = (form) => {
  const errors = {}

  const regex = {
    stockType: /^[A-Za-zÑñÁáÉéÍíÓóÚúÜü\s]+$/,
    quantity: /^(0|[1-9]\d*)$/,
    productId: /.*/,
    stockId: /.*/
  }

  const message = {
    stockType: "El campo 'StockType' solo acepta mayusculas, minusculas y espacios en blanco",
    quantity: 'Solo se aceptan stock positivos y 0',
    productId: '',
    stockId: ''
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

export const EditProductStockForm = ({ product }) => {
  const EDIT_STOCK_ENDPOINT = `http://localhost:8080/product/${product.stockId}/stock`
  const initialForm = {
    stockType: product.stockType,
    quantity: product.quantity.toString(),
    productId: product.productId.toString(),
    stockId: product.stockId.toString()
  }
  const {
    form, errors, loading, response,
    handleChange, handleBlur, handleSubmit
  } = useFrom(initialForm, validationsForm, helpHttp().put, EDIT_STOCK_ENDPOINT)

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
          name='stockType'
          placeholder='Tipo de Stock'
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
        <br /><br />
        <input type='submit' value='Editar' />
      </form>
      {loading && <Loader />}
    </>
  )
}

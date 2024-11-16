import { useState } from 'react'
import { helpHttp } from '../helpers/helpHttp'
import Loader from './Loader'

export const OrderCard = ({ order, numeroDeOrden }) => {
  const [loading, setLoading] = useState(false)

  const handleToggleState = () => {
    setLoading(true)
    helpHttp().put(
      'endpint cambiar estado',
      {
        headers: {
          'Content-Type': 'Application/json',
          Accept: 'application/json'
        }
      })
      .then(res => {
        setLoading(false)
      })
    return () => {
    }
  }

  return (
    <>
      <article className='product-card'>
        <h4>Orden {numeroDeOrden}</h4>
        <p>Estado: {order.order_state}</p>
        <p>Fecha de creacion: {order.creation_date}</p>
        <p>Fecha de confirmacion: {order.confirmation_date}</p>
        {window.sessionStorage.getItem('rol') === 'ADMIN' &&
          loading
          ? <Loader />
          : <button onClick={handleToggleState}>Cambiar Estado</button>}
      </article>
      <ul className='porperties-list'>
        {order.product_list.map((product, index) => (
          <li key={index}>{product.name} - {product.quantity}</li>
        ))}
      </ul>
    </>
  )
}

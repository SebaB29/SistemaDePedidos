import { helpHttp } from '../helpers/helpHttp'

const totalPrice = (order) => {
  let sum = 0
  order.product_list.forEach(el => {
    sum += Number(el.price) * Number(el.quantity)
  })
  return sum
}

export const OrderCard = ({ order }) => {
  const options = {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: 'numeric',
    minute: 'numeric',
    hour12: false
  }
  const formattedDate = new Intl.DateTimeFormat('es-ES', options).format(new Date(order.creation_date))

  const handleChangeState = (state) => {
    helpHttp().pat(
      'http://localhost:8080/order/state',
      {
        body: {
          user_id: window.sessionStorage.getItem('user_id'),
          order_id: order.order_id,
          order_state: state,
          products: order.product_list
        },
        headers: {
          'Content-Type': 'Application/json',
          Accept: 'application/json'
        }
      })
      .then(res => {
        if (res.status === 'OK') {
          window.alert(res.data.message)
          window.location.reload()
        } else {
          window.alert(res.error)
        }
      })
    return () => {
    }
  }

  return (
    <>
      <article className='product-card'>
        <h4>Orden {order.order_id}</h4>
        <p>Estado: {order.order_state}</p>
        <p>Fecha de creacion: {formattedDate}</p>
        <p>Total a pagar: ${totalPrice(order)}</p>
        {window.sessionStorage.getItem('rol') === 'ADMIN' &&
          <>
            <button onClick={() => handleChangeState(1)}>Cambiar a En Proceso</button>
            <button onClick={() => handleChangeState(2)}>Cambiar a Enviado</button>
          </>}
        {order.order_state === 'Confirmado' && <button onClick={() => handleChangeState(3)}>Cancelar</button>}
      </article>
      <ul className='porperties-list'>
        {order.product_list.map((product, index) => (
          <li key={index}>{product.name} - {product.quantity} x ${product.price}</li>
        ))}
      </ul>
    </>
  )
}

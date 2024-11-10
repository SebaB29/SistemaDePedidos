import { useEffect, useState } from 'react'
import Header from '../components/Header'
import Loader from '../components/Loader'
import { Main } from '../components/Main'
import { OrderCard } from '../components/OrderCard'
import { helpHttp } from '../helpers/helpHttp'

const initialOrders = [
  {
    id: 0,
    user: {
      name: 'usuario',
      last_name: '1'
    },
    state: 'ENVIADO',
    products: [
      {
        id: 0,
        name: 'Pan',
        amount: 3
      },
      {
        id: 1,
        name: 'agua',
        amount: 2
      }
    ]
  },
  {
    id: 1,
    user: {
      name: 'usuario',
      last_name: '2'
    },
    state: 'EN PROGRESO',
    products: [
      {
        id: 0,
        name: 'Pan',
        amount: 1
      }
    ]
  }
]

export const Orders = () => {
  const [loading, setLoading] = useState(false)
  const [orders, setProducts] = useState(initialOrders)
  // let ENDPOINT = 'endpoint para usuario normal'
  // if (window.sessionStorage.getItem('admin')) ENDPOINT = 'edpoint admin'
  // chequear si es admin para hacer un fetch de los pedidos suyos o de todos

  // useEffect(() => {
  //   setLoading(true)
  //   helpHttp().get(
  //     ENDPOINT,
  //     {
  //       headers: {
  //         'Content-Type': 'Application/json',
  //         Accept: 'application/json'
  //       }
  //     })
  //     .then(res => {
  //       setLoading(false)
  //       setProducts(res)
  //     })
  //   return () => {
  //   }
  // }, [])

  return (
    <>
      <Header title='Ordenes' />
      <Main>
        <br />
        {loading && <Loader />}
        {orders.map((order) => (
          <OrderCard
            key={order.id}
            userName={order.user.name}
            userLastName={order.user.last_name}
            state={order.state}
            products={order.products}
          />
        ))}
      </Main>
    </>
  )
}

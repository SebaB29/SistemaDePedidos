import { useState } from 'react'
import { helpHttp } from '../helpers/helpHttp'
import Loader from './Loader'
import Modal from './Modal'
import { useModal } from '../hooks/useModal'
import { EditProductForm } from './EditProductForm'

const BUY_ENDPOINT = ''
const DELETE_ENDPOINT = ''

export const ProductCard = ({ product }) => {
  const [loading, setLoading] = useState(false)
  const [isOpenModal, openModal, closeModal] = useModal(false)

  const handleBuy = (e) => {
    let amount
    while (!/^(0|[1-9][0-9]*)$/.test(amount)) {
      amount = window.prompt('Cuantos quieres comprar?')
      if (amount === null) return
    }
    setLoading(true)
    helpHttp().post(
      BUY_ENDPOINT,
      {
        body: {
          id: product.id,
          amount
        },
        headers: {
          'Content-Type': 'Application/json',
          Accept: 'application/json'
        }
      })
      .then(res => {
        setLoading(false)
        window.alert(res)
      })
  }

  const handleDelete = () => {
    helpHttp().del(
      DELETE_ENDPOINT,
      {
        body: {
          id: product.id
        },
        headers: {
          'Content-Type': 'Application/json',
          Accept: 'application/json'
        }
      })
      .then(res => {
        window.alert(res)
      })
    window.location.reload()
  }

  return (
    <>
      <article className='product-card'>
        <h4>{product.name}</h4>
        <p>Stock: {product.stock}</p>
        {loading
          ? <Loader />
          : <button onClick={handleBuy}>Comprar</button>}
        {window.sessionStorage.getItem('admin')
          ? <><button onClick={openModal}>Editar</button><button onClick={handleDelete}>Eliminar</button></>
          : <></>}
      </article>
      <ul className='porperties-list'>
        {product.attributes.map((attribute, index) => (
          <li key={index}>{attribute.description} : {attribute.value}</li>
        ))}
      </ul>

      <Modal isOpen={isOpenModal} closeModal={closeModal}>
        <h3>Editar {product.name}</h3>
        <EditProductForm product={product} />
      </Modal>
    </>
  )
}

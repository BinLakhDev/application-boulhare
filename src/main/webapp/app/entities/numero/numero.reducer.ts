import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INumero, defaultValue } from 'app/shared/model/numero.model';

export const ACTION_TYPES = {
  FETCH_NUMERO_LIST: 'numero/FETCH_NUMERO_LIST',
  FETCH_NUMERO: 'numero/FETCH_NUMERO',
  CREATE_NUMERO: 'numero/CREATE_NUMERO',
  UPDATE_NUMERO: 'numero/UPDATE_NUMERO',
  DELETE_NUMERO: 'numero/DELETE_NUMERO',
  RESET: 'numero/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INumero>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type NumeroState = Readonly<typeof initialState>;

// Reducer

export default (state: NumeroState = initialState, action): NumeroState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NUMERO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NUMERO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NUMERO):
    case REQUEST(ACTION_TYPES.UPDATE_NUMERO):
    case REQUEST(ACTION_TYPES.DELETE_NUMERO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NUMERO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NUMERO):
    case FAILURE(ACTION_TYPES.CREATE_NUMERO):
    case FAILURE(ACTION_TYPES.UPDATE_NUMERO):
    case FAILURE(ACTION_TYPES.DELETE_NUMERO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NUMERO_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NUMERO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NUMERO):
    case SUCCESS(ACTION_TYPES.UPDATE_NUMERO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NUMERO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/numeros';

// Actions

export const getEntities: ICrudGetAllAction<INumero> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_NUMERO_LIST,
    payload: axios.get<INumero>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<INumero> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NUMERO,
    payload: axios.get<INumero>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INumero> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NUMERO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INumero> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NUMERO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INumero> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NUMERO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

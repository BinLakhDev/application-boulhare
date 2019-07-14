import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBanque, defaultValue } from 'app/shared/model/banque.model';

export const ACTION_TYPES = {
  FETCH_BANQUE_LIST: 'banque/FETCH_BANQUE_LIST',
  FETCH_BANQUE: 'banque/FETCH_BANQUE',
  CREATE_BANQUE: 'banque/CREATE_BANQUE',
  UPDATE_BANQUE: 'banque/UPDATE_BANQUE',
  DELETE_BANQUE: 'banque/DELETE_BANQUE',
  SET_BLOB: 'banque/SET_BLOB',
  RESET: 'banque/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBanque>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type BanqueState = Readonly<typeof initialState>;

// Reducer

export default (state: BanqueState = initialState, action): BanqueState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BANQUE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BANQUE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BANQUE):
    case REQUEST(ACTION_TYPES.UPDATE_BANQUE):
    case REQUEST(ACTION_TYPES.DELETE_BANQUE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BANQUE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BANQUE):
    case FAILURE(ACTION_TYPES.CREATE_BANQUE):
    case FAILURE(ACTION_TYPES.UPDATE_BANQUE):
    case FAILURE(ACTION_TYPES.DELETE_BANQUE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BANQUE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BANQUE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BANQUE):
    case SUCCESS(ACTION_TYPES.UPDATE_BANQUE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BANQUE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/banques';

// Actions

export const getEntities: ICrudGetAllAction<IBanque> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_BANQUE_LIST,
    payload: axios.get<IBanque>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IBanque> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BANQUE,
    payload: axios.get<IBanque>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBanque> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BANQUE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBanque> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BANQUE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBanque> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BANQUE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

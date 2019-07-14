import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IClientInstantane, defaultValue } from 'app/shared/model/client-instantane.model';

export const ACTION_TYPES = {
  FETCH_CLIENTINSTANTANE_LIST: 'clientInstantane/FETCH_CLIENTINSTANTANE_LIST',
  FETCH_CLIENTINSTANTANE: 'clientInstantane/FETCH_CLIENTINSTANTANE',
  CREATE_CLIENTINSTANTANE: 'clientInstantane/CREATE_CLIENTINSTANTANE',
  UPDATE_CLIENTINSTANTANE: 'clientInstantane/UPDATE_CLIENTINSTANTANE',
  DELETE_CLIENTINSTANTANE: 'clientInstantane/DELETE_CLIENTINSTANTANE',
  RESET: 'clientInstantane/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IClientInstantane>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ClientInstantaneState = Readonly<typeof initialState>;

// Reducer

export default (state: ClientInstantaneState = initialState, action): ClientInstantaneState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CLIENTINSTANTANE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CLIENTINSTANTANE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CLIENTINSTANTANE):
    case REQUEST(ACTION_TYPES.UPDATE_CLIENTINSTANTANE):
    case REQUEST(ACTION_TYPES.DELETE_CLIENTINSTANTANE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CLIENTINSTANTANE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CLIENTINSTANTANE):
    case FAILURE(ACTION_TYPES.CREATE_CLIENTINSTANTANE):
    case FAILURE(ACTION_TYPES.UPDATE_CLIENTINSTANTANE):
    case FAILURE(ACTION_TYPES.DELETE_CLIENTINSTANTANE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLIENTINSTANTANE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLIENTINSTANTANE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CLIENTINSTANTANE):
    case SUCCESS(ACTION_TYPES.UPDATE_CLIENTINSTANTANE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CLIENTINSTANTANE):
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

const apiUrl = 'api/client-instantanes';

// Actions

export const getEntities: ICrudGetAllAction<IClientInstantane> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CLIENTINSTANTANE_LIST,
    payload: axios.get<IClientInstantane>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IClientInstantane> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CLIENTINSTANTANE,
    payload: axios.get<IClientInstantane>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IClientInstantane> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CLIENTINSTANTANE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IClientInstantane> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CLIENTINSTANTANE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IClientInstantane> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CLIENTINSTANTANE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUtilisateur, defaultValue } from 'app/shared/model/utilisateur.model';

export const ACTION_TYPES = {
  FETCH_UTILISATEUR_LIST: 'utilisateur/FETCH_UTILISATEUR_LIST',
  FETCH_UTILISATEUR: 'utilisateur/FETCH_UTILISATEUR',
  CREATE_UTILISATEUR: 'utilisateur/CREATE_UTILISATEUR',
  UPDATE_UTILISATEUR: 'utilisateur/UPDATE_UTILISATEUR',
  DELETE_UTILISATEUR: 'utilisateur/DELETE_UTILISATEUR',
  SET_BLOB: 'utilisateur/SET_BLOB',
  RESET: 'utilisateur/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUtilisateur>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type UtilisateurState = Readonly<typeof initialState>;

// Reducer

export default (state: UtilisateurState = initialState, action): UtilisateurState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_UTILISATEUR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_UTILISATEUR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_UTILISATEUR):
    case REQUEST(ACTION_TYPES.UPDATE_UTILISATEUR):
    case REQUEST(ACTION_TYPES.DELETE_UTILISATEUR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_UTILISATEUR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_UTILISATEUR):
    case FAILURE(ACTION_TYPES.CREATE_UTILISATEUR):
    case FAILURE(ACTION_TYPES.UPDATE_UTILISATEUR):
    case FAILURE(ACTION_TYPES.DELETE_UTILISATEUR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_UTILISATEUR_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_UTILISATEUR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_UTILISATEUR):
    case SUCCESS(ACTION_TYPES.UPDATE_UTILISATEUR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_UTILISATEUR):
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

const apiUrl = 'api/utilisateurs';

// Actions

export const getEntities: ICrudGetAllAction<IUtilisateur> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_UTILISATEUR_LIST,
    payload: axios.get<IUtilisateur>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IUtilisateur> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_UTILISATEUR,
    payload: axios.get<IUtilisateur>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUtilisateur> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_UTILISATEUR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUtilisateur> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_UTILISATEUR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUtilisateur> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_UTILISATEUR,
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

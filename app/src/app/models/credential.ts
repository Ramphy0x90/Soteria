import { Entity } from "./entity";

export interface Credential {
  id: number | null;
  entity: Entity;
  userName: string;
  password: string;
}
